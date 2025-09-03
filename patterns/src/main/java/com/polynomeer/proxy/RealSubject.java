package com.polynomeer.proxy;

public class RealSubject implements Subject {
    @Override
    public String request(String key) {
        System.out.println("RealSubject: 실제 작업 수행");
        return "data-for-" + key;
    }
}
