package com.polynomeer.collection;

public class HashTableMain {
    public static void main(String[] args) {
        HashTable map = new HashTable();
        map.put("apple", 100);
        map.put("banana", 200);
        System.out.println(map.get("apple"));
    }
}
