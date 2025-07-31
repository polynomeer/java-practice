package com.polynomeer.strategy;

import java.util.Arrays;

public class SortMain {
    public static void main(String[] args) {
        int[] data = {5, 2, 8, 3, 9};

        Sorter sorter = new Sorter(new BubbleSort());
        sorter.sort(data);
        System.out.println("정렬 결과: " + Arrays.toString(data));

        sorter = new Sorter(new QuickSort());
        data = new int[]{5, 2, 9, 1, 7};
        sorter.sort(data);
        System.out.println("정렬 결과: " + Arrays.toString(data));

        sorter.setStrategy(new MergeSort());
        data = new int[]{3, 6, 4, 8, 2};
        sorter.sort(data);
        System.out.println("정렬 결과: " + Arrays.toString(data));

        sorter.setStrategy(new InsertionSort());
        data = new int[]{10, 3, 5, 2, 1};
        sorter.sort(data);
        System.out.println("정렬 결과: " + Arrays.toString(data));
    }
}
