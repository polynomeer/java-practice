package com.polynomeer.gc;

import java.util.*;

public class ZgcSimulator {
    private final List<ZgcObject> heap = new ArrayList<>();
    private final List<ZgcObject> roots = new ArrayList<>();
    private final Map<ZgcObject, ZgcObject> relocationMap = new HashMap<>();

    public ZgcObject allocate(String name) {
        ZgcObject obj = new ZgcObject(name);
        heap.add(obj);
        return obj;
    }

    public void addRoot(ZgcObject obj) {
        roots.add(obj);
    }

    public void runGc() {
        System.out.println("\n=== ZGC 시작 ===");

        mark();
        relocate();
        remap();

        System.out.println("[ZGC] 최종 힙 상태:");
        for (ZgcObject obj : heap) {
            System.out.println("  " + obj);
        }
        System.out.println("================\n");
    }

    private void mark() {
        System.out.println("[1] 마킹 단계 (Mark)");

        Queue<ZgcObject> worklist = new LinkedList<>(roots);
        for (ZgcObject root : roots) {
            root.setColor(ZgcObject.Color.GRAY);
        }

        while (!worklist.isEmpty()) {
            ZgcObject obj = worklist.poll();
            obj.setColor(ZgcObject.Color.BLACK);
            for (ZgcObject ref : obj.getReferences()) {
                if (ref.getColor() == ZgcObject.Color.WHITE) {
                    ref.setColor(ZgcObject.Color.GRAY);
                    worklist.add(ref);
                }
            }
        }

        printHeapState();
    }

    private void relocate() {
        System.out.println("[2] 이동 단계 (Relocate)");
        relocationMap.clear();

        List<ZgcObject> newHeap = new ArrayList<>();

        for (ZgcObject obj : heap) {
            if (obj.getColor() == ZgcObject.Color.BLACK) {
                ZgcObject copy = new ZgcObject(obj.toString());
                relocationMap.put(obj, copy);
                newHeap.add(copy);
            }
        }

        heap.clear();
        heap.addAll(newHeap);
        printRelocationMap();
    }

    private void remap() {
        System.out.println("[3] 리맵 단계 (Remap)");

        for (ZgcObject copy : heap) {
            List<ZgcObject> oldRefs = new ArrayList<>(copy.getReferences());
            copy.getReferences().clear();
            for (ZgcObject ref : oldRefs) {
                ZgcObject mapped = relocationMap.getOrDefault(ref, ref);
                copy.addReference(mapped);
            }
        }

        // 루트 업데이트
        for (int i = 0; i < roots.size(); i++) {
            ZgcObject updated = relocationMap.get(roots.get(i));
            if (updated != null) {
                roots.set(i, updated);
            }
        }

        System.out.println("[ZGC] 루트 remap 완료");
    }

    private void printHeapState() {
        System.out.println("[Heap 상태]");
        for (ZgcObject obj : heap) {
            System.out.println("  " + obj);
        }
    }

    private void printRelocationMap() {
        System.out.println("[Relocation Map]");
        for (Map.Entry<ZgcObject, ZgcObject> entry : relocationMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " → " + entry.getValue());
        }
    }
}
