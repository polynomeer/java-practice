package com.polynomeer.deadlock;

// DeadlockDemo.java

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class DeadLockDemo {

    private static final ReentrantLock lockA = new ReentrantLock();
    private static final ReentrantLock lockB = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        // 두 스레드가 첫 번째 락을 잡은 뒤 동시에 두 번째 락을 시도하도록 동기화
        CountDownLatch bothHaveFirstLock = new CountDownLatch(2);

        Thread t1 = new Thread(() -> {
            lockA.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " acquired lockA");
                bothHaveFirstLock.countDown();
                await(bothHaveFirstLock);
                System.out.println(Thread.currentThread().getName() + " trying to acquire lockB");
                lockB.lock(); // <-- Deadlock 발생
                try {
                    System.out.println("won't reach here");
                } finally {
                    lockB.unlock();
                }
            } finally {
                lockA.unlock();
            }
        }, "T1");

        Thread t2 = new Thread(() -> {
            lockB.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " acquired lockB");
                bothHaveFirstLock.countDown();
                await(bothHaveFirstLock);
                System.out.println(Thread.currentThread().getName() + " trying to acquire lockA");
                lockA.lock(); // 여기서 서로 기다리며 데드락
                try {
                    System.out.println("won't reach here");
                } finally {
                    lockA.unlock();
                }
            } finally {
                lockB.unlock();
            }
        }, "T2");

        t1.start();
        t2.start();

        // 간단한 데드락 감지 루프 (JVM의 ThreadMXBean 사용)
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        while (true) {
            long[] ids = mbean.findDeadlockedThreads();
            if (ids != null && ids.length > 0) {
                System.out.println("\n==== DEADLOCK DETECTED ====");
                ThreadInfo[] infos = mbean.getThreadInfo(ids, true, true);
                for (ThreadInfo info : infos) {
                    System.out.println(info); // 교착관계/대기 그래프를 출력
                }
                System.out.println("============================\n");
                // 데드락 상태를 보여준 뒤 프로세스를 종료
                System.exit(0);
            }
            Thread.sleep(200);
        }
    }

    private static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
