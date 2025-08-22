package com.polynomeer.mutex;

public class MutexDemo {
    public static void main(String[] args) throws InterruptedException {
        final Mutex mutex = new Mutex(true); // 공정(fair) 모드

        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            log(name + " tries to lock...");
            mutex.lock();
            try {
                log(name + " acquired lock 🔑");

                // 임계구역 (작업 시뮬레이션)
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                log(name + " working inside critical section...");

            } finally {
                mutex.unlock();
                log(name + " released lock 🔓");
            }
        };

        // 스레드 3개 실행
        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");
        Thread t3 = new Thread(task, "T3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        log("All threads finished.");
    }

    private static void log(String msg) {
        System.out.printf("[%d] %s%n", System.currentTimeMillis() % 100000, msg);
    }
}
