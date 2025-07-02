package com.polynomeer.gc;

public class GenerationalGcMain {
    public static void main(String[] args) {
        GenerationalGc gc = new GenerationalGc();

        // 객체 생성
        GcObject a = gc.allocate("A");
        GcObject b = gc.allocate("B");
        GcObject c = gc.allocate("C");
        GcObject d = gc.allocate("D");
        GcObject e = gc.allocate("E"); // 고아 객체 (GC 대상)

        // 참조 연결
        a.addReference(b);
        b.addReference(c);
        c.addReference(d);

        // GC Root 설정
        gc.addRoot(a);

        System.out.println("== 초기 상태 ==");
        System.out.println("YoungGen: A, B, C, D, E");

        // Minor GC 수행
        gc.minorGc();

        // 객체 하나 제거해 GC 유도
        b.removeReference(c); // C
        // , D는 GC 대상이 됨
        System.out.println("\n== C에 대한 참조 제거 후 Minor GC ==");
        gc.minorGc();

        // Full GC 수행 (Young + Old 모두 정리)
        System.out.println("\n== Full GC 실행 ==");
        gc.fullGc();
    }
}
