package com.polynomeer.gc;

import java.util.*;

public class MarkAndSweepGc {
    private final List<GcObject> heap = new ArrayList<>();
    private final List<GcObject> roots = new ArrayList<>();

    public List<GcObject> getHeap() {
        return heap;
    }

    public List<GcObject> getRoots() {
        return roots;
    }

    public GcObject allocate(String name) {
        GcObject obj = new GcObject(name);
        heap.add(obj);
        return obj;
    }

    public void addRoot(GcObject obj) {
        roots.add(obj);
    }

    /**
     * 🔵 Mark Phase
     */
    public void mark() {
        System.out.println("[GC] Mark phase...");
        for (GcObject root : roots) {
            root.mark();
        }
    }

    /**
     * 🔴 Sweep Phase
     */
    public void sweep() {
        System.out.println("[GC] Sweep phase...");
        Iterator<GcObject> iterator = heap.iterator();
        while (iterator.hasNext()) {
            GcObject obj = iterator.next();
            if (!obj.isMarked()) {
                System.out.println("[GC] Collecting " + obj);
                iterator.remove();
            } else {
                obj.unmark(); // reset for next GC cycle
            }
        }
        System.out.println("[GC] Remaining objects in heap: " + heap);
    }

    /**
     * 전체 수동 GC 실행 (선택적 호출)
     */
    public void collect() {
        System.out.println("[GC] Starting Mark and Sweep...");
        mark();
        sweep();
    }
}
