package com.polynomeer.gc;

import java.util.*;

public class GenerationalGc {
    private final List<GcObject> youngGen = new ArrayList<>();
    private final List<GcObject> oldGen = new ArrayList<>();
    private final List<GcObject> roots = new ArrayList<>();

    public GcObject allocate(String name) {
        GcObject obj = new GcObject(name);
        youngGen.add(obj);
        return obj;
    }

    public void addRoot(GcObject obj) {
        roots.add(obj);
    }

    public void minorGc() {
        System.out.println("[Minor GC] 시작");
        Set<GcObject> live = new HashSet<>();
        for (GcObject root : roots) {
            dfs(root, live, youngGen);
        }
        youngGen.retainAll(live);  // 죽은 객체 제거

        // 생존 객체를 Old로 Promote
        for (GcObject obj : live) {
            if (!oldGen.contains(obj)) {
                oldGen.add(obj);
            }
        }

        System.out.println("[Minor GC] 종료. Young: " + youngGen + ", Old: " + oldGen);
    }

    public void fullGc() {
        System.out.println("[Full GC] 시작");
        Set<GcObject> live = new HashSet<>();
        for (GcObject root : roots) {
            dfs(root, live, new ArrayList<>(youngGen));
            dfs(root, live, new ArrayList<>(oldGen));
        }
        youngGen.retainAll(live);
        oldGen.retainAll(live);
        System.out.println("[Full GC] 종료. 남은 객체: Y=" + youngGen + " / O=" + oldGen);
    }

    private void dfs(GcObject obj, Set<GcObject> visited, List<GcObject> target) {
        if (!target.contains(obj) || visited.contains(obj)) return;
        visited.add(obj);
        for (GcObject ref : obj.getReferences()) {
            dfs(ref, visited, target);
        }
    }
}
