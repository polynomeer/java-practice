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

    /**
     * 1. Synchronous + Blocking
     * - 호출한 쪽(Main)이 직접 I/O가 끝날 때까지 기다림
     * - 순차적 흐름이며, 다음 작업은 완료 후에야 실행됨
     * - 시뮬레이션: Thread.sleep을 이용한 대기 + 진행 표시줄 출력
     */
    static void synchronousBlocking() throws InterruptedException {
        System.out.println("[App] Starting blocking task...");
        simulateProgress("Processing", 2000);
        System.out.println("[App] Task complete!");
    }

    /**
     * 2. Synchronous + Non-blocking
     * - 호출자는 I/O 결과가 준비됐는지 직접 계속 체크 (Polling)
     * - 실제로는 데이터를 기다리지 않으며, 확인만 반복
     * - 효율은 낮지만, 개념적으로 논블로킹의 기초를 보여줌
     */
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

    /**
     * 3. Asynchronous + Blocking
     * - 작업은 백그라운드 스레드에서 수행되고, 메인 스레드는 즉시 반환
     * - 백그라운드에서는 blocking 방식으로 작업이 완료될 때까지 기다림
     * - 메인 스레드와 작업 수행을 병렬로 실행
     */
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

    /**
     * 4. Asynchronous + Non-blocking
     * - 비동기적으로 실행되며, 내부도 논블로킹 방식으로 처리
     * - 데이터가 준비될 때까지 백그라운드에서 반복 체크
     * - 메인 스레드는 즉시 반환하고, 전체 처리는 병렬적으로 이뤄짐
     */
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

    /**
     * Blocking 작업 시뮬레이션
     * - 일정 시간 동안 작업 중인 것처럼 보이도록 진행 바 출력
     * - 실제 I/O 대기를 가정하여 Thread.sleep 사용
     *
     * @param label      출력할 태스크 이름
     * @param durationMs 총 대기 시간 (밀리초)
     * @throws InterruptedException sleep 중 인터럽트될 경우
     */
    static void simulateProgress(String label, int durationMs) throws InterruptedException {
        int steps = durationMs / 200;
        for (int i = 0; i < steps; i++) {
            System.out.print("\r" + label + " " + progressBar(i, steps));
            Thread.sleep(200);
        }
        System.out.println();
    }

    /**
     * Non-blocking I/O 시뮬레이션
     * - 데이터 준비 여부를 랜덤으로 결정 (70% 확률로 아직 준비 안됨)
     * - 실제 논블로킹 호출의 반환 값을 흉내냄
     *
     * @return true: 데이터 준비됨 / false: 아직 준비되지 않음
     */
    static boolean simulateNonBlockingIO() {
        return Math.random() > 0.7;
    }

    /**
     * progress bar 생성
     * - 콘솔에 현재 작업 상태를 시각적으로 표시
     * - 진행 상태를 [===>    ] 형태로 반환
     *
     * @param current 현재 진행 위치
     * @param total   총 단계 수
     * @return progress bar 문자열
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
