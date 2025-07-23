package com.polynomeer.collection;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class ListBenchmark {
    private static final int N_THREADS = 10;
    private static final int N_ELEMENTS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting benchmark...\n");

        benchmark("Vector", new Vector<>());
        benchmark("CopyOnWriteArrayList", new CopyOnWriteArrayList<>());
    }

    public static void benchmark(String name, List<Integer> list) throws InterruptedException {
        System.out.println("Benchmarking: " + name);

        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
        CountDownLatch latch = new CountDownLatch(N_THREADS * 2);
        AtomicLong sum = new AtomicLong(0);

        long startTime = System.currentTimeMillis();

        // 쓰기 스레드
        for (int i = 0; i < N_THREADS; i++) {
            executor.execute(() -> {
                for (int j = 0; j < N_ELEMENTS; j++) {
                    list.add(j);
                }
                latch.countDown();
            });
        }

        // 읽기 스레드
        executor.execute(() -> {
            long localSum = 0;
            for (int k = 0; k < 10; k++) {
                synchronized (list) {
                    for (Integer val : list) {
                        localSum += val;
                    }
                }
            }
            sum.addAndGet(localSum);
            latch.countDown();
        });

        latch.await();
        executor.shutdown();

        long endTime = System.currentTimeMillis();
        System.out.printf("Total time: %d ms | Final size: %d | Sum: %d\n\n",
                (endTime - startTime), list.size(), sum.get());
    }
}
