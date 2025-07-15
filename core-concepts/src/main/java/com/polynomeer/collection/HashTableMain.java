package com.polynomeer.collection;

public class HashTableMain {
    public static void main(String[] args) {
        HashTable<String, Integer> map = new HashTable<>();

        map.put("apple", 100);
        map.put("banana", 200);
        map.put("orange", 300);
        map.put("apple", 400); // 덮어쓰기

        System.out.println("apple = " + map.get("apple"));
        System.out.println("banana = " + map.get("banana"));
        System.out.println("grape = " + map.get("grape"));

        map.remove("banana");
        System.out.println("contains banana? " + map.containsKey("banana"));
        System.out.println("size = " + map.size());
    }
}
