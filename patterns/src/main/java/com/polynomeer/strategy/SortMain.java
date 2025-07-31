package com.polynomeer.strategy;

public class SortMain {
    public static void main(String[] args) {
        int[] data = {5, 2, 8, 3};

        Sorter sorter = new Sorter(new BubbleSort());
        sorter.sort(data);
        System.out.println("정렬 결과: " + java.util.Arrays.toString(data));

        // 전략 변경
        data = new int[]{9, 1, 6, 4}; // 새 데이터
        sorter.setStrategy(new QuickSort());
        sorter.sort(data);
        System.out.println("정렬 결과: " + java.util.Arrays.toString(data));
    }
}
