package com.polynomeer.strategy;

public class InsertionSort implements SortStrategy {

    @Override
    public void sort(int[] arr) {
        System.out.println("Insertion Sort 수행");

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }
}
