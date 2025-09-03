package com.polynomeer.proxy;

public class ProxyMain {
    public static void main(String[] args) {
        Subject service = new CachingProxy(new RealSubject());
        System.out.println(service.request("A")); // Real 호출
        System.out.println(service.request("A")); // 캐시 히트
    }
}