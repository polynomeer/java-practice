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
        System.out.println("\n[GC] 🚀 Copying GC 시작");

        System.out.println("\n[GC] 📦 fromSpace (GC 이전 상태):");
        visualize(fromSpace);

        toSpace.clear();
        Set<GcObject> copied = new HashSet<>();

        for (GcObject root : roots) {
            copy(root, copied);
        }

        System.out.println("\n[GC] ✅ 복사 완료: toSpace 상태");
        visualize(toSpace);

        fromSpace = new ArrayList<>(toSpace);  // swap
        System.out.println("[GC] 📦 fromSpace <- toSpace 교체 완료");
    }

    private void copy(GcObject obj, Set<GcObject> copied) {
        if (copied.contains(obj)) return;

        System.out.println("[GC] 복사: " + obj);
        copied.add(obj);
        toSpace.add(obj);

        for (GcObject ref : obj.getReferences()) {
            copy(ref, copied);
        }
    }

    public List<GcObject> getFromSpace() {
        return fromSpace;
    }

    public List<GcObject> getToSpace() {
        return toSpace;
    }

    public List<GcObject> getRoots() {
        return roots;
    }

    public void visualize(List<GcObject> space) {
        Set<GcObject> visited = new HashSet<>();
        for (GcObject obj : space) {
            if (!visited.contains(obj)) {
                dfs(obj, "", visited);
            }
        }
    }

    private void dfs(GcObject obj, String indent, Set<GcObject> visited) {
        if (!visited.add(obj)) {
            System.out.println(indent + "↳ " + obj + " (↻)");
            return;
        }

        System.out.println(indent + "↳ " + obj);
        for (GcObject ref : obj.getReferences()) {
            dfs(ref, indent + "   ", visited);
        }
    }
}
