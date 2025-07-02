package com.polynomeer.gc;

public class MarkAndSweepMain {
    public static void main(String[] args) {
        MarkAndSweepGc gc = new MarkAndSweepGc();

        // 객체 그래프 구성
        GcObject a = gc.allocate("A");
        GcObject b = gc.allocate("B");
        GcObject c = gc.allocate("C");
        GcObject d = gc.allocate("D");
        GcObject e = gc.allocate("E");

        a.addReference(b);
        b.addReference(c);
        c.addReference(d);
        // E는 루트에서 도달 불가능한 객체

        gc.addRoot(a);

        System.out.println("== 초기 상태 ==");
        GcObjectGraphVisualizer.visualize(gc.getHeap());

        System.out.println("\n== Mark-and-Sweep GC 수행 ==");
        gc.collect();

        System.out.println("\n== GC 이후 상태 ==");
        GcObjectGraphVisualizer.visualize(gc.getHeap());
    }
}
