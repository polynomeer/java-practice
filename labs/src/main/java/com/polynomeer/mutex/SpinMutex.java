package com.polynomeer.mutex;

import java.util.concurrent.atomic.AtomicBoolean;

final class SpinMutex {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private volatile Thread owner;

    /**
     * 바쁜 대기(spin)로 락을 획득 (짧은 임계구역에서만 사용 권장)
     */
    public void lock() {
        while (!locked.compareAndSet(false, true)) {
            // JDK 9+ : CPU 친화적 힌트
            Thread.onSpinWait();
        }
        owner = Thread.currentThread();
    }

    /**
     * 즉시 시도
     */
    public boolean tryLock() {
        if (locked.compareAndSet(false, true)) {
            owner = Thread.currentThread();
            return true;
        }
        return false;
    }

    /**
     * 락 해제
     */
    public void unlock() {
        if (owner != Thread.currentThread()) {
            throw new IllegalMonitorStateException("Lock owner only can unlock");
        }
        owner = null;               // 순서 중요: 소유자 해제 -> 락 풀기
        locked.set(false);
    }

    public boolean isLocked() {
        return locked.get();
    }

    public boolean isHeldByCurrentThread() {
        return owner == Thread.currentThread();
    }
}
