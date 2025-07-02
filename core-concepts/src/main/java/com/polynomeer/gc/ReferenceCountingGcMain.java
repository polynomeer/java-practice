package com.polynomeer.gc;

public class ReferenceCountingGcMain {
    public static void main(String[] args) {
        ReferenceCountingGc gc = new ReferenceCountingGc();

        // 객체 생성
        GcObject a = gc.allocate("A");
        GcObject b = gc.allocate("B");
        GcObject c = gc.allocate("C");
        GcObject d = gc.allocate("D");

        // 참조 관계 설정
        gc.addReference(a, b); // A → B
        gc.addReference(b, c); // B → C
        gc.addReference(c, d); // C → D

        System.out.println("== 초기 상태 ==");
        gc.show();

        // 참조 제거: A → B 끊기 → B 참조 0 → B, C, D 모두 GC됨
        System.out.println("\n== A → B 참조 제거 ==");
        gc.removeReference(a, b);
        gc.show();

        // 순환 참조 테스트
        System.out.println("\n== 순환 참조 테스트 ==");
        GcObject x = gc.allocate("X");
        GcObject y = gc.allocate("Y");
        gc.addReference(x, y);
        gc.addReference(y, x); // 순환 참조 X ↔ Y

        gc.show();

        // 외부 참조 제거 (순환만 남음)
        System.out.println("\n== X 참조 제거 ==");
        gc.removeReference(y, x); // 하지만 y → x는 아직 살아 있음
        gc.removeReference(x, y); // 이제 외부에서 도달 불가지만 refCount는 1씩

        gc.show(); // ✴️ 수거되지 않음 (Reference Counting의 한계)
    }
}
