package com.polynomeer.strategy;

public class QuickSort implements SortStrategy {
    @Override
    public void sort(int[] arr) {
        System.out.println("Quick Sort 수행");
        java.util.Arrays.sort(arr);
    }
}

