package com.polynomeer.collection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoadFactorTest {

    static final int N = 100_000; // 테스트할 엔트리 수
    static final int INIT_CAPACITY = 128;

    public static void main(String[] args) throws Exception {
        testWithLoadFactor(0.5f);
        testWithLoadFactor(0.75f);
        testWithLoadFactor(1.0f);
    }

    private static void testWithLoadFactor(float loadFactor) throws Exception {
        System.out.println("===== Load Factor: " + loadFactor + " =====");

        Map<Integer, String> map = new HashMap<>(INIT_CAPACITY, loadFactor);
        Random random = new Random(42);
        int[] keys = new int[N];

        for (int i = 0; i < N; i++) {
            keys[i] = random.nextInt(Integer.MAX_VALUE);
        }

        long startPut = System.nanoTime();
        for (int i = 0; i < N; i++) {
            map.put(keys[i], "value" + i);
        }
        long timePut = System.nanoTime() - startPut;

        long startGet = System.nanoTime();
        for (int i = 0; i < N; i++) {
            map.get(keys[i]);
        }
        long timeGet = System.nanoTime() - startGet;

        // 실제 테이블 접근 (리플렉션)
        // Java 9 이후에는 VM 옵션을 추가해야함 "--add-opens java.base/java.util=ALL-UNNAMED"
        Field tableField = HashMap.class.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(map);

        int totalBuckets = 0;
        int totalCollisions = 0;
        int maxChainLength = 0;

        for (Object bucket : table) {
            if (bucket == null) continue;
            int chainLength = 0;

            // Node는 HashMap 내부 static class
            Field nextField = bucket.getClass().getDeclaredField("next");
            nextField.setAccessible(true);

            Object current = bucket;
            while (current != null) {
                chainLength++;
                current = nextField.get(current);
            }

            totalBuckets++;
            if (chainLength > 1) {
                totalCollisions += (chainLength - 1);
                maxChainLength = Math.max(maxChainLength, chainLength);
            }
        }

        System.out.println("Total entries inserted: " + N);
        System.out.println("Put time: " + (timePut / 1_000_000) + " ms");
        System.out.println("Get time: " + (timeGet / 1_000_000) + " ms");
        System.out.println("Table size (buckets): " + table.length);
        System.out.println("Used buckets: " + totalBuckets);
        System.out.println("Total collisions: " + totalCollisions);
        System.out.println("Max chain length in a bucket: " + maxChainLength);
        System.out.println();
    }
}
