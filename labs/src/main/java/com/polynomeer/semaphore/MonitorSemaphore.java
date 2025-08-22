package com.polynomeer.semaphore;

/**
 * 교육용: synchronized + wait/notify로 구현한 카운팅 세마포어
 */
class MonitorSemaphore {
    private int permits;

    public MonitorSemaphore(int permits) {
        if (permits < 0) throw new IllegalArgumentException();
        this.permits = permits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) wait();
        permits--;
    }

    public synchronized void acquireUninterruptibly() {
        boolean interrupted = false;
        while (permits == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        permits--;
        if (interrupted) Thread.currentThread().interrupt(); // 인터럽트 상태 복원
    }

    public synchronized boolean tryAcquire() {
        if (permits > 0) {
            permits--;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        permits++;
        notify(); // 하나만 깨우면 충분(여러 개면 notifyAll 고려)
    }

    public synchronized void release(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        permits += n;
        for (int i = 0; i < n; i++) notify();
    }

    public synchronized int availablePermits() {
        return permits;
    }
}
