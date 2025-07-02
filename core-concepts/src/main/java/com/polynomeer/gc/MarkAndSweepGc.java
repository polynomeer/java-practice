package com.polynomeer.gc;

import java.util.*;

public class MarkAndSweepGc {
    private final List<GcObject> heap = new ArrayList<>();
    private final List<GcObject> roots = new ArrayList<>();

    public List<GcObject> getHeap() {
        return heap;
    }

    public GcObject allocate(String name) {
        GcObject obj = new GcObject(name);
        heap.add(obj);
        return obj;
    }

    public void addRoot(GcObject obj) {
        roots.add(obj);
    }

    public void collect() {
        System.out.println("[GC] Starting Mark and Sweep...");

        // 1. Mark phase
        for (GcObject root : roots) {
            root.mark();
        }

        // 2. Sweep phase
        Iterator<GcObject> iterator = heap.iterator();
        while (iterator.hasNext()) {
            GcObject obj = iterator.next();
            if (!obj.isMarked()) {
                System.out.println("[GC] Collecting " + obj);
                iterator.remove();
            } else {
                obj.unmark(); // reset for next cycle
            }
        }

        System.out.println("[GC] Remaining objects in heap: " + heap);
    }
}
