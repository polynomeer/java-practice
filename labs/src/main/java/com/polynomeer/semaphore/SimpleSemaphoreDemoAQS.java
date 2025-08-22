package com.polynomeer.semaphore;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleSemaphoreDemoAQS {
    static void log(String msg) {
        long t = System.currentTimeMillis() % 100000;
        System.out.printf("[%5d] %-4s %s%n", t, Thread.currentThread().getName(), msg);
    }

    public static void main(String[] args) throws InterruptedException {
        final int PERMITS = 2;          // 동시에 2개만 입장
        final boolean FAIR = true;      // 공정 큐
        final SimpleSemaphore sem = new SimpleSemaphore(PERMITS, FAIR);

        Runnable worker = () -> {
            log("wants a permit (available=" + sem.availablePermits() + ")");
            try {
                sem.acquire(); // 또는 tryAcquire(…)로 타임아웃 테스트
                log("acquired ✅ (available=" + sem.availablePermits() + ")");
                // 임계 구역: 제한된 자원 사용
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(300, 700));
                } catch (InterruptedException ignored) {
                }
                log("working...");
            } catch (InterruptedException e) {
                log("interrupted while waiting");
                return;
            } finally {
                sem.release();
                log("released 🔓 (available=" + sem.availablePermits() + ")");
            }
        };

        int N = 6;
        Thread[] ts = new Thread[N];
        for (int i = 0; i < N; i++) {
            ts[i] = new Thread(worker, "T" + (i + 1));
            ts[i].start();
            Thread.sleep(60); // 시작 시점을 약간 흩뿌려 로그가 보기 좋게
        }
        for (Thread t : ts) t.join();

        log("drainPermits demo → drained=" + sem.drainPermits());
        log("final available=" + sem.availablePermits());
    }
}

