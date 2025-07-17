package com.polynomeer.collection;

public class ArrayListMain {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");

        System.out.println("size: " + list.size());
        System.out.println("index 1: " + list.get(1));

        list.remove(1);
        System.out.println("after remove: " + list.get(1));
    }
}
