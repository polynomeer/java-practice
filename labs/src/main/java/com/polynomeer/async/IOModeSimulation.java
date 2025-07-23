package com.polynomeer.async;

public class IOModeSimulation {
    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. Synchronous + Blocking ===");
        synchronousBlocking();

        System.out.println("\n=== 2. Synchronous + Non-blocking ===");
        synchronousNonBlocking();

        System.out.println("\n=== 3. Asynchronous + Blocking ===");
        asynchronousBlocking();

        System.out.println("\n=== 4. Asynchronous + Non-blocking ===");
        asynchronousNonBlocking();

        // 대기: 비동기 작업 완료용
        Thread.sleep(4000);
    }

    // 1. Synchronous + Blocking
    static void synchronousBlocking() throws InterruptedException {
        System.out.println("Start task");
        simulateBlockingIO();  // 실제로 기다림
        System.out.println("Task complete");
    }

    // 2. Synchronous + Non-blocking (Polling)
    static void synchronousNonBlocking() throws InterruptedException {
        System.out.println("Start polling task");
        int attempts = 0;
        while (!simulateNonBlockingIO()) {
            System.out.println("Data not ready, retrying...");
            Thread.sleep(200);
            attempts++;
            if (attempts > 10) {
                System.out.println("Gave up after 10 tries");
                return;
            }
        }
        System.out.println("Data is ready, processing done.");
    }

    // 3. Asynchronous + Blocking (별도 쓰레드 내부는 blocking)
    static void asynchronousBlocking() {
        System.out.println("Start async task (blocking inside)");
        new Thread(() -> {
            try {
                simulateBlockingIO();
                System.out.println("Async Task complete (blocking)");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("Main thread continues");
    }

    // 4. Asynchronous + Non-blocking (이벤트 기반)
    static void asynchronousNonBlocking() {
        System.out.println("Start async task (non-blocking)");
        new Thread(() -> {
            try {
                while (!simulateNonBlockingIO()) {
                    System.out.println("Async: Not ready yet...");
                    Thread.sleep(200);
                }
                System.out.println("Async Task complete (non-blocking)");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("Main thread continues");
    }

    // ----------- 시뮬레이션 메서드 ------------

    // Blocking I/O 시뮬레이션 (2초 기다림)
    static void simulateBlockingIO() throws InterruptedException {
        Thread.sleep(2000);
    }

    // Non-blocking I/O 시뮬레이션 (50% 확률로 준비됨)
    static boolean simulateNonBlockingIO() {
        return Math.random() > 0.5;
    }
}
