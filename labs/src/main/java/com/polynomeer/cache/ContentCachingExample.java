package com.polynomeer.cache;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

/**
 * 가변 TTL 캐시 + 인기(히트) 기반 TTL 적용 예시
 */
public class ContentCachingExample {

    public static void main(String[] args) throws Exception {
        // 예시: 원본 저장소(예: DB, 마이크로서비스 API 등)
        ContentRepository repo = id -> "Video#" + id + " payload at " + Instant.now();

        // 인기 측정기: 최근 60초 창에서 히트 수로 평가
        PopularityTracker tracker = new PopularityTracker(Duration.ofSeconds(60));

        // 티어 정책: 히트 기준과 TTL
        TierPolicy policy = new TierPolicy(
                50, Duration.ofMinutes(10),   // HOT: 최근 60초 50회 이상 → 10분 TTL
                10, Duration.ofMinutes(2),    // WARM: 10~49회 → 2분 TTL
                Duration.ofSeconds(30)        // COLD: <10회 → 30초 TTL
        );

        // 캐시 생성
        AdaptiveTtlCache<String, String> cache = new AdaptiveTtlCache<>(policy);

        // 서비스
        ContentService service = new ContentService(repo, tracker, cache);

        // 데모: 여러 번 조회해서 티어가 올라가며 TTL이 늘어나는지 확인
        String id = "abc123";

        // 초기 몇 번 조회 (COLD → WARM → HOT로 승급)
        for (int i = 1; i <= 55; i++) {
            String v = service.getContent(id);
            if (i % 10 == 0 || i == 55) {
                Tier t = policy.classify(tracker.recentHits(id));
                System.out.printf("[%s] count=%d, tier=%s, cacheTTL=%s%n",
                        Instant.now(), tracker.recentHits(id), t,
                        cache.debugTtlRemaining(id).map(Duration::toString).orElse("N/A"));
            }
            Thread.sleep(30); // 데모용 딜레이
        }

        // 청소기 종료를 원하면 아래 호출
        // cache.shutdown();
    }

    /* ------------------------ Domain layer ------------------------ */

    interface ContentRepository {
        String fetch(String contentId);
    }

    static final class ContentService {
        private final ContentRepository repo;
        private final PopularityTracker popularity;
        private final AdaptiveTtlCache<String, String> cache;

        ContentService(ContentRepository repo,
                       PopularityTracker popularity,
                       AdaptiveTtlCache<String, String> cache) {
            this.repo = repo;
            this.popularity = popularity;
            this.cache = cache;
        }

        public String getContent(String contentId) {
            // 1) 히트 기록
            popularity.recordHit(contentId);

            // 2) 최근 창에서의 히트 수로 티어 결정
            int hits = popularity.recentHits(contentId);
            Tier tier = cache.policy.classify(hits);

            // 3) 캐시 조회/적재 (티어별 TTL 적용, 더 뜨거워지면 만료 연장)
            return cache.get(contentId, tier, () -> repo.fetch(contentId));
        }
    }

    /* ------------------------ Tier policy ------------------------ */

    enum Tier {HOT, WARM, COLD}

    static final class TierPolicy {
        private final int hotThreshold;
        private final int warmThreshold;
        private final Duration hotTtl;
        private final Duration warmTtl;
        private final Duration coldTtl;

        public TierPolicy(int hotThreshold, Duration hotTtl,
                          int warmThreshold, Duration warmTtl,
                          Duration coldTtl) {
            if (hotThreshold <= warmThreshold)
                throw new IllegalArgumentException("hotThreshold > warmThreshold 이어야 합니다.");
            this.hotThreshold = hotThreshold;
            this.warmThreshold = warmThreshold;
            this.hotTtl = Objects.requireNonNull(hotTtl);
            this.warmTtl = Objects.requireNonNull(warmTtl);
            this.coldTtl = Objects.requireNonNull(coldTtl);
        }

        public Tier classify(int hitsInWindow) {
            if (hitsInWindow >= hotThreshold) return Tier.HOT;
            if (hitsInWindow >= warmThreshold) return Tier.WARM;
            return Tier.COLD;
        }

        public Duration ttlFor(Tier tier) {
            return switch (tier) {
                case HOT -> hotTtl;
                case WARM -> warmTtl;
                case COLD -> coldTtl;
            };
        }
    }

    /* ------------------------ Popularity (recent window hits) ------------------------ */

    /**
     * 최근 시간 창(window) 안의 히트 수를 유지하는 간단한 구현.
     * 각 key별로 히트 타임스탬프 큐를 관리하고, 조회 시 창 밖의 오래된 항목을 제거합니다.
     */
    static final class PopularityTracker {
        private final Duration window;
        private final ConcurrentMap<String, Deque<Long>> hits = new ConcurrentHashMap<>();

        public PopularityTracker(Duration window) {
            this.window = window;
        }

        public void recordHit(String key) {
            long now = System.currentTimeMillis();
            Deque<Long> q = hits.computeIfAbsent(key, k -> new ArrayDeque<>());
            synchronized (q) {
                q.addLast(now);
                prune(q, now);
            }
        }

        public int recentHits(String key) {
            long now = System.currentTimeMillis();
            Deque<Long> q = hits.get(key);
            if (q == null) return 0;
            synchronized (q) {
                prune(q, now);
                return q.size();
            }
        }

        private void prune(Deque<Long> q, long nowMs) {
            long cutoff = nowMs - window.toMillis();
            while (!q.isEmpty() && q.peekFirst() < cutoff) {
                q.removeFirst();
            }
        }
    }

    /* ------------------------ Adaptive TTL Cache ------------------------ */

    static final class AdaptiveTtlCache<K, V> {
        private final ConcurrentMap<K, Entry<V>> map = new ConcurrentHashMap<>();
        private final ScheduledExecutorService janitor;
        final TierPolicy policy;

        public AdaptiveTtlCache(TierPolicy policy) {
            this.policy = policy;
            // 주기적으로 만료 청소
            this.janitor = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "adaptive-ttl-janitor");
                t.setDaemon(true);
                return t;
            });
            this.janitor.scheduleAtFixedRate(this::cleanup, 5, 5, TimeUnit.SECONDS);
        }

        public V get(K key, Tier tier, Loader<V> loader) {
            Duration ttl = policy.ttlFor(tier);
            long now = System.currentTimeMillis();

            // 1) 캐시에 있으면 반환 + 필요 시 만료 시간 연장(승급)
            Entry<V> e = map.get(key);
            if (e != null) {
                if (e.isExpiredAt(now)) {
                    // 만료됐으면 재적재
                    V val = loader.load();
                    long newExpiry = now + ttl.toMillis();
                    Entry<V> neu = new Entry<>(val, newExpiry, tier);
                    map.put(key, neu);
                    return val;
                } else {
                    // 살아있으면, 티어가 더 높아져 TTL이 더 길다면 만료 연장
                    Duration remain = Duration.ofMillis(e.expireAt - now);
                    if (ttl.compareTo(remain) > 0) {
                        e.expireAt = now + ttl.toMillis();
                        e.tier = tier;
                    }
                    return e.value;
                }
            }

            // 2) 없으면 로드 후 삽입
            V val = loader.load();
            long expireAt = now + ttl.toMillis();
            Entry<V> neu = new Entry<>(val, expireAt, tier);
            map.put(key, neu);
            return val;
        }

        /**
         * 디버그용: 남은 TTL
         */
        public Optional<Duration> debugTtlRemaining(K key) {
            Entry<V> e = map.get(key);
            if (e == null) return Optional.empty();
            long now = System.currentTimeMillis();
            if (e.isExpiredAt(now)) return Optional.of(Duration.ZERO);
            return Optional.of(Duration.ofMillis(e.expireAt - now));
        }

        private void cleanup() {
            long now = System.currentTimeMillis();
            for (Map.Entry<K, Entry<V>> me : map.entrySet()) {
                if (me.getValue().isExpiredAt(now)) {
                    map.remove(me.getKey(), me.getValue());
                }
            }
        }

        public void shutdown() {
            janitor.shutdownNow();
        }

        static final class Entry<V> {
            volatile V value;
            volatile long expireAt;
            volatile Tier tier;

            Entry(V value, long expireAt, Tier tier) {
                this.value = value;
                this.expireAt = expireAt;
                this.tier = tier;
            }

            boolean isExpiredAt(long nowMs) {
                return nowMs >= expireAt;
            }
        }

        interface Loader<V> {
            V load();
        }
    }
}
