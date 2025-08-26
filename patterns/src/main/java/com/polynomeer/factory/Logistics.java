package com.polynomeer.factory;

abstract class Logistics {
    // 팩토리 메서드 — 서브클래스가 어떤 Product를 만들지 결정
    protected abstract Transport createTransport();

    // 템플릿 메서드 — 공통 알고리즘 흐름(“사용” 로직)을 제공
    public final void planDelivery(String cargo) {
        // 공통 준비 로직
        System.out.println("[Logistics] Checking capacity, scheduling...");

        // 구체 제품 생성은 서브클래스에 위임
        Transport transport = createTransport();

        // 공통 후처리 로직 + 제품 사용
        String result = transport.deliver(cargo);
        System.out.println("[Logistics] Done -> " + result);
    }
}