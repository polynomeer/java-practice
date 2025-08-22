package com.polynomeer.mutex;

import java.util.concurrent.ThreadLocalRandom;

/**
 * SpinMutex 동작을 시각적으로 보여주는 데모
 */
public class SpinMutexDemo {

    // 공유자원: 안전하게 1씩 증가해야 함 (레이스 없음을 숫자로 확인)
    static int sharedCounter = 0;

    static void log(String msg) {
        long now = System.currentTimeMillis() % 100000;
        System.out.printf("[%5d] %-6s %s%n", now, Thread.currentThread().getName(), msg);
    }

    public static void main(String[] args) throws InterruptedException {
        final SpinMutex spin = new SpinMutex();

        // 스레드 수 / 각 스레드 작업 횟수
        final int THREADS = 4;
        final int WORKS_PER_THREAD = 5;

        Runnable task = () -> {
            for (int i = 1; i <= WORKS_PER_THREAD; i++) {
                log("tries to lock...");
                spin.lock();
                try {
                    log("acquired lock 🔑  (locked=" + spin.isLocked() + ")");
                    // ---- 임계구역 시작 ----
                    int before = sharedCounter;
                    // 짧은 작업 시뮬레이션 (스핀락에 적합하게 아주 짧게 유지)
                    busyWork(2000, 5000); // 몇 천 번 정도의 아주 짧은 루프
                    sharedCounter = before + 1;
                    log("critical section: counter " + before + " → " + sharedCounter);
                    // ---- 임계구역 끝 ----
                } finally {
                    spin.unlock();
                    log("released lock 🔓  (locked=" + spin.isLocked() + ")");
                }

                // 임계구역 밖(비임계)에서 랜덤 대기 — 경합 패턴을 보기 좋게 하기 위함
                sleepRandom(30, 120);
            }
        };

        Thread[] ts = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            ts[i] = new Thread(task, "T" + (i + 1));
            ts[i].start();
        }
        for (Thread t : ts) t.join();

        System.out.println("------------------------------------------------");
        System.out.println("Expected counter = " + (THREADS * WORKS_PER_THREAD));
        System.out.println("Actual   counter = " + sharedCounter);
        System.out.println((sharedCounter == THREADS * WORKS_PER_THREAD)
                ? "OK: no lost updates ✅" : "ERROR: race detected ❌");
    }

    /**
     * 아주 짧은 바쁜 연산으로 임계구역 길이를 몇 μs 수준으로 유지
     */
    static void busyWork(int minIters, int maxIters) {
        int bound = ThreadLocalRandom.current().nextInt(minIters, maxIters);
        long x = 0;
        for (int i = 0; i < bound; i++) x += i; // 의미 없는 계산
        if (x == 42) System.out.print(""); // JIT 최적화 방지용 무의미 코드
    }

    /**
     * 임계구역 밖에서 랜덤 sleep
     */
    static void sleepRandom(int minMs, int maxMs) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(minMs, maxMs));
        } catch (InterruptedException ignored) {
        }
    }
}
