package com.polynomeer.gc;

import java.util.*;

public class CopyingGc {
    private List<GcObject> fromSpace = new ArrayList<>();
    private List<GcObject> toSpace = new ArrayList<>();
    private final List<GcObject> roots = new ArrayList<>();

    public GcObject allocate(String name) {
        GcObject obj = new GcObject(name);
        fromSpace.add(obj);
        return obj;
    }

    public void addRoot(GcObject obj) {
        roots.add(obj);
    }

    public void collect() {
        System.out.println("[GC] Copying GC 시작");

        toSpace.clear();
        Set<GcObject> copied = new HashSet<>();

        for (GcObject root : roots) {
            copy(root, copied);
        }

        System.out.println("[GC] From → To 공간 복사 완료");
        fromSpace = new ArrayList<>(toSpace);  // swap
        System.out.println("[GC] 남은 객체: " + fromSpace);
    }

    private void copy(GcObject obj, Set<GcObject> copied) {
        if (copied.contains(obj)) return;
        copied.add(obj);
        toSpace.add(obj);
        for (GcObject ref : obj.getReferences()) {
            copy(ref, copied);
        }
    }
}
