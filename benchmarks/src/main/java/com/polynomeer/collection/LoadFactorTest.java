package com.polynomeer.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoadFactorTest {

    static final int N = 100_000; // 테스트할 요소 수
    static final int INIT_CAPACITY = 128; // 낮게 잡아 리사이즈 유도

    public static void main(String[] args) {
        testLoadFactor(0.5f);
        testLoadFactor(0.75f);
        testLoadFactor(1.0f);
    }

    private static void testLoadFactor(float loadFactor) {
        System.out.println("==== Load Factor: " + loadFactor + " ====");

        Map<Integer, String> map = new HashMap<>(INIT_CAPACITY, loadFactor);

        int[] bucketCollisions = new int[1 << 16]; // 예상 최대 테이블 크기
        Random rand = new Random(42);

        int collisionCount = 0;
        int resizeThreshold = (int) (INIT_CAPACITY * loadFactor);
        int resizeCount = 0;

        for (int i = 0; i < N; i++) {
            int key = rand.nextInt(Integer.MAX_VALUE);
            map.put(key, "value");

            // 해시 테이블 충돌 추정 (단순 시뮬레이션)
            int hash = hash(key);
            int index = (INIT_CAPACITY - 1) & hash;

            if (bucketCollisions[index] > 0) collisionCount++;
            bucketCollisions[index]++;

            // 임계값 초과 체크 (단순 추정용)
            if (map.size() > resizeThreshold) {
                resizeThreshold *= 2;
                resizeCount++;
            }
        }

        System.out.println("Total Inserted: " + map.size());
        System.out.println("Estimated Collisions: " + collisionCount);
        System.out.println("Estimated Resizes: " + resizeCount);
        System.out.println();
    }

    // HashMap의 hash() 함수와 유사하게 비트 섞기
    static int hash(Object key) {
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }
}
