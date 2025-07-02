package com.polynomeer.gc;

import java.util.*;

public class ReferenceCountingGc {
    private final Map<GcObject, Integer> refCount = new HashMap<>();
    private final List<GcObject> heap = new ArrayList<>();

    public GcObject allocate(String name) {
        GcObject obj = new GcObject(name);
        heap.add(obj);
        refCount.put(obj, 0);
        return obj;
    }

    public void addReference(GcObject from, GcObject to) {
        from.addReference(to);
        refCount.put(to, refCount.get(to) + 1);
    }

    public void removeReference(GcObject from, GcObject to) {
        from.getReferences().remove(to);
        refCount.put(to, refCount.get(to) - 1);
        if (refCount.get(to) == 0) {
            collect(to);
        }
    }

    private void collect(GcObject obj) {
        System.out.println("[GC] 수거됨: " + obj);
        heap.remove(obj);
        for (GcObject ref : obj.getReferences()) {
            refCount.put(ref, refCount.get(ref) - 1);
            if (refCount.get(ref) == 0) {
                collect(ref);
            }
        }
    }

    public void show() {
        System.out.println("현재 힙: " + heap);
    }
}
