package com.polynomeer.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachingProxy implements Subject {
    private final Subject real; // 합성
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public CachingProxy(Subject real) {
        this.real = real;
    }

    @Override
    public String request(String key) {
        return cache.computeIfAbsent(key, k -> {
            System.out.println("Proxy: 캐시에 없음 → Real 호출");
            return real.request(k);
        });
    }
}