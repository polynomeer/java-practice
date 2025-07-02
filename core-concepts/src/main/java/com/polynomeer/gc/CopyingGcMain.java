package com.polynomeer.gc;

public class CopyingGcMain {
    public static void main(String[] args) {
        CopyingGc gc = new CopyingGc();

        // 객체 생성
        GcObject a = gc.allocate("A");
        GcObject b = gc.allocate("B");
        GcObject c = gc.allocate("C");
        GcObject d = gc.allocate("D");
        GcObject x = gc.allocate("X"); // 고아 객체

        // 참조 설정
        a.addReference(b);
        b.addReference(c);
        c.addReference(d);

        gc.addRoot(a);

        // 복사형 GC 수행
        gc.collect();
    }
}
