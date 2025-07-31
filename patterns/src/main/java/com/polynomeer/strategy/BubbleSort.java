package com.polynomeer.strategy;

public class BubbleSort implements SortStrategy {
    @Override
    public void sort(int[] arr) {
        System.out.println("Bubble Sort 수행");
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
