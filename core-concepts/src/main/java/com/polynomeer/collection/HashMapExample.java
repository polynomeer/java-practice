package com.polynomeer.collection;

import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();

        map.put("apple", 100);
        map.put("banana", 200);
        map.put("orange", 300);

        System.out.println(map.get("apple"));
        System.out.println(map.containsKey("banana"));

        map.remove("orange");

        for (String key : map.keySet()) {
            System.out.println(key + " => " + map.get(key));
        }
    }
}
