package com.polynomeer.semaphore;

public class MonitorSemaphoreDemo {
    static void log(String m) {
        long t = System.currentTimeMillis() % 100000;
        System.out.printf("[%5d] %-4s %s%n", t, Thread.currentThread().getName(), m);
    }

    public static void main(String[] args) throws InterruptedException {
        final MonitorSemaphore sem = new MonitorSemaphore(1); // 이진 세마포어처럼 동작
        Runnable job = () -> {
            log("waits...");
            try {
                sem.acquire();
                log("acquired 🔑");
                try {
                    Thread.sleep(400);
                } catch (InterruptedException ignored) {
                }
            } catch (InterruptedException e) {
                log("interrupted");
            } finally {
                sem.release();
                log("released 🔓");
            }
        };

        Thread a = new Thread(job, "A");
        Thread b = new Thread(job, "B");
        Thread c = new Thread(job, "C");
        a.start();
        b.start();
        c.start();
        a.join();
        b.join();
        c.join();
    }
}
