package com.polynomeer.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class SafeLockDemo {

    private static final ReentrantLock lockA = new ReentrantLock();
    private static final ReentrantLock lockB = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("== 방식 A: 일관된 락 순서 (A -> B) ==");
        runWithConsistentOrdering();

        Thread.sleep(500);

        System.out.println("\n== 방식 B: tryLock + 타임아웃 + 백오프 ==");
        runWithTryLockTimeout();
    }

    /* =========================
       방식 A: 일관된 락 순서
       ========================= */
    private static void runWithConsistentOrdering() throws InterruptedException {
        Thread t1 = new Thread(() -> doWorkOrdered(lockA, lockB), "A-then-B-Worker-1");
        Thread t2 = new Thread(() -> doWorkOrdered(lockA, lockB), "A-then-B-Worker-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("방식 A 종료: 데드락 없이 정상 완료");
    }

    private static void doWorkOrdered(ReentrantLock first, ReentrantLock second) {
        // 항상 같은 순서(first -> second)로만 획득
        first.lock();
        try {
            log("acquired first");
            // 비즈니스 로직 일부…
            sleepQuiet(50);

            second.lock();
            try {
                log("acquired second");
                // 크리티컬 섹션
                sleepQuiet(100);
            } finally {
                second.unlock();
                log("released second");
            }
        } finally {
            first.unlock();
            log("released first");
        }
    }

    /* ==========================================
       방식 B: tryLock + 타임아웃 + 지수 백오프(무순서)
       ========================================== */
    private static void runWithTryLockTimeout() throws InterruptedException {
        Thread t1 = new Thread(() -> doWorkTryLock(lockA, lockB), "Worker-X");
        Thread t2 = new Thread(() -> doWorkTryLock(lockB, lockA), "Worker-Y");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("방식 B 종료: 데드락 없이 재시도로 완료");
    }

    private static void doWorkTryLock(ReentrantLock left, ReentrantLock right) {
        long baseBackoffMs = 10;     // 초기 백오프
        long maxBackoffMs = 200;    // 최대 백오프
        int attempts = 0;

        while (true) {
            attempts++;
            boolean gotLeft = false;
            boolean gotRight = false;

            try {
                gotLeft = left.tryLock(100, TimeUnit.MILLISECONDS);
                if (!gotLeft) {
                    backoff(baseBackoffMs, maxBackoffMs, attempts);
                    continue;
                }
                log("acquired left");

                // left를 잡은 상태에서 right 시도
                gotRight = right.tryLock(100, TimeUnit.MILLISECONDS);
                if (!gotRight) {
                    // 교착 고리를 방지하기 위해 즉시 해제 후 백오프
                    log("could not acquire right; releasing left and backing off");
                    left.unlock();
                    gotLeft = false;
                    backoff(baseBackoffMs, maxBackoffMs, attempts);
                    continue;
                }

                // 둘 다 획득 성공 → 크리티컬 섹션
                log("acquired right");
                sleepQuiet(80);
                log("critical section done");
                return; // 정상 종료

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                if (gotRight) {
                    right.unlock();
                    log("released right");
                }
                if (gotLeft) {
                    left.unlock();
                    log("released left");
                }
            }
        }
    }

    private static void backoff(long base, long max, int attempts) {
        long exp = Math.min(max, (long) (base * Math.pow(2, Math.min(6, attempts)))); // 지수 백오프 상한
        long jitter = ThreadLocalRandom.current().nextLong(base, exp + 1);
        sleepQuiet(jitter);
    }

    private static void sleepQuiet(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    private static void log(String msg) {
        System.out.printf("[%s] %s%n", Thread.currentThread().getName(), msg);
    }
}
