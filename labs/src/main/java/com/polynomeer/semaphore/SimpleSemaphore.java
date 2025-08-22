package com.polynomeer.semaphore;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * JUC Semaphore 스타일의 카운팅 세마포어 (공정/비공정 지원)
 */
public class SimpleSemaphore {
    /**
     * state = 남은 permit 수
     */
    private static final class Sync extends AbstractQueuedSynchronizer {
        private final boolean fair;

        Sync(int permits, boolean fair) {
            if (permits < 0) throw new IllegalArgumentException("permits < 0");
            setState(permits);
            this.fair = fair;
        }

        int getPermits() {
            return getState();
        }

        boolean isFair() {
            return fair;
        }

        @Override
        protected int tryAcquireShared(int acquires) {
            if (acquires <= 0) throw new IllegalArgumentException();
            // 공정 모드면 앞선 대기자에게 양보
            if (fair && hasQueuedPredecessors()) return -1;

            for (; ; ) {
                int available = getState();
                int remaining = available - acquires;
                if (remaining < 0) return -1;            // 실패 → 대기
                if (compareAndSetState(available, remaining))
                    return 1;                            // 성공(0 이상)
            }
        }

        @Override
        protected boolean tryReleaseShared(int releases) {
            if (releases <= 0) throw new IllegalArgumentException();
            for (; ; ) {
                int current = getState();
                int next = current + releases;
                if (next < current) throw new Error("permit overflow");
                if (compareAndSetState(current, next))
                    return true;                         // 대기자 깨우기
            }
        }

        int drainPermits() {
            for (; ; ) {
                int current = getState();
                if (current == 0) return 0;
                if (compareAndSetState(current, 0))
                    return current;
            }
        }

        void reducePermits(int reductions) {
            if (reductions < 0) throw new IllegalArgumentException();
            for (; ; ) {
                int current = getState();
                int next = current - reductions;
                if (next > current) throw new Error("underflow");
                if (next < 0) throw new IllegalArgumentException("reductions > available");
                if (compareAndSetState(current, next)) return;
            }
        }
    }

    private final Sync sync;

    public SimpleSemaphore(int permits) {
        this(permits, false);
    }

    public SimpleSemaphore(int permits, boolean fair) {
        this.sync = new Sync(permits, fair);
    }

    // 획득 API
    public void acquire() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public void acquireUninterruptibly() {
        sync.acquireShared(1);
    }

    public boolean tryAcquire() {
        return sync.tryAcquireShared(1) >= 0;
    }

    public boolean tryAcquire(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    // 해제 API
    public void release() {
        sync.releaseShared(1);
    }

    public void release(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        sync.releaseShared(n);
    }

    // 유틸
    public int availablePermits() {
        return sync.getPermits();
    }

    public int drainPermits() {
        return sync.drainPermits();
    }

    public void reducePermits(int reductions) {
        sync.reducePermits(reductions);
    }

    public boolean isFair() {
        return sync.isFair();
    }
}

/**
 * 이진 세마포어(뮤텍스 대용)
 */
final class BinarySemaphore extends SimpleSemaphore {
    public BinarySemaphore() {
        super(1, false);
    }

    public BinarySemaphore(boolean fair) {
        super(1, fair);
    }
}
