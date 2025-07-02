package com.polynomeer.gc;

public class MarkAndSweepMain {
    public static void main(String[] args) {
        MarkAndSweepGc gc = new MarkAndSweepGc();

        // 객체 생성
        GcObject a = gc.allocate("A");
        GcObject b = gc.allocate("B");
        GcObject c = gc.allocate("C");
        GcObject d = gc.allocate("D");
        GcObject e = gc.allocate("E"); // 고아 객체

        // 참조 관계 설정
        a.addReference(b);
        b.addReference(c);
        c.addReference(d);
        // E는 어떤 참조도 없음

        // GC Root 설정
        gc.addRoot(a);

        System.out.println("== 1. 초기 상태 ==");
        GcObjectGraphVisualizer.visualize(gc.getHeap());

        System.out.println("\n== 2. Mark 단계 ==");
        gc.mark();
        GcObjectGraphVisualizer.visualize(gc.getHeap());

        System.out.println("\n== 3. Sweep 단계 ==");
        gc.sweep();
        GcObjectGraphVisualizer.visualize(gc.getHeap());

        System.out.println("\n== 4. 최종 상태 (Heap) ==");
        GcObjectGraphVisualizer.visualize(gc.getHeap());
    }
}
