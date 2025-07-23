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

        Thread.sleep(5000); // 대기: 비동기 작업 완료용
    }

    // 1. Sync + Blocking
    static void synchronousBlocking() throws InterruptedException {
        System.out.println("[App] Starting blocking task...");
        simulateProgress("Processing", 2000);
        System.out.println("[App] Task complete!");
    }

    // 2. Sync + Non-blocking
    static void synchronousNonBlocking() throws InterruptedException {
        System.out.println("[App] Starting polling loop...");
        int attempts = 0;
        while (!simulateNonBlockingIO()) {
            System.out.println("[App] Attempt " + (attempts + 1) + ": No data yet...");
            Thread.sleep(300);
            attempts++;
            if (attempts >= 10) {
                System.out.println("[App] Gave up after 10 tries");
                return;
            }
        }
        System.out.println("[App] Data is ready. Processing done.");
    }

    // 3. Async + Blocking (Thread 내부 blocking)
    static void asynchronousBlocking() {
        System.out.println("[App] Launching async blocking task...");
        new Thread(() -> {
            try {
                simulateProgress("[Worker] Processing (blocking)", 2000);
                System.out.println("[Worker] Task complete!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("[App] Main thread continues...");
    }

    // 4. Async + Non-blocking (Polling with callback style)
    static void asynchronousNonBlocking() {
        System.out.println("[App] Launching async non-blocking task...");
        new Thread(() -> {
            try {
                int attempt = 0;
                while (!simulateNonBlockingIO()) {
                    System.out.println("[AsyncWorker] Attempt " + (++attempt) + ": waiting...");
                    Thread.sleep(300);
                }
                System.out.println("[AsyncWorker] Data ready, task complete!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("[App] Main thread continues...");
    }

    // ---------- 유틸 ----------

    // 시뮬레이션: Blocking 작업 표시
    static void simulateProgress(String label, int durationMs) throws InterruptedException {
        int steps = durationMs / 200;
        for (int i = 0; i < steps; i++) {
            System.out.print("\r" + label + " " + progressBar(i, steps));
            Thread.sleep(200);
        }
        System.out.println();
    }

    /**
     * Non-blocking I/O - 70% 확률로 데이터 준비됨
     *
     * @return random 값이 0.7 이상인 경우 true (70% 확률)
     */
    static boolean simulateNonBlockingIO() {
        return Math.random() > 0.7;
    }

    /**
     * @param current
     * @param total
     * @return
     */
    static String progressBar(int current, int total) {
        StringBuilder bar = new StringBuilder();
        bar.append("[");
        for (int i = 0; i < total; i++) {
            if (i < current)
                bar.append("=");
            else if (i == current)
                bar.append(">");
            else
                bar.append(" ");
        }
        bar.append("]");
        return bar.toString();
    }
}
