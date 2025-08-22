package com.polynomeer.mutex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

final class Mutex {
    /**
     * 내부 동기화기: 상태 0=unlock, 1=lock
     */
    private static final class Sync extends AbstractQueuedSynchronizer {
        private final boolean fair;

        Sync(boolean fair) {
            this.fair = fair;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1 && getExclusiveOwnerThread() == Thread.currentThread();
        }

        @Override
        protected boolean tryAcquire(int acquires) {
            // 공정 모드면 선행 대기자 있으면 양보
            if (fair && hasQueuedPredecessors()) return false;

            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false; // 비재진입: 이미 잠겨 있으면 실패
        }

        @Override
        protected boolean tryRelease(int releases) {
            if (getState() == 0 || getExclusiveOwnerThread() != Thread.currentThread()) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true; // 대기자에게 신호
        }

        boolean isLocked() {
            return getState() == 1;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final Sync sync;

    /**
     * 기본(비공정)
     */
    Mutex() {
        this(false);
    }

    /**
     * 공정성 옵션
     */
    Mutex(boolean fair) {
        this.sync = new Sync(fair);
    }

    public void lock() {
        sync.acquire(1);
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    public void unlock() {
        sync.release(1);
    }

    public boolean isLocked() {
        return sync.isLocked();
    }

    public boolean isHeldByCurrentThread() {
        return sync.isHeldExclusively();
    }

    /**
     * 조건변수(조건 대기/알림)
     */
    public Condition newCondition() {
        return sync.newCondition();
    }
}
