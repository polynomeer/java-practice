package com.polynomeer.gc;

import java.util.*;

public class CopyingGcMain {

    public static void main(String[] args) {
        GcObject a = new GcObject("A");
        GcObject b = new GcObject("B");
        GcObject c = new GcObject("C");
        GcObject d = new GcObject("D");
        GcObject e = new GcObject("E");

        a.addReference(b);
        b.addReference(c);
        c.addReference(d);
        // E는 루트에서 도달 불가능

        List<GcObject> fromSpace = Arrays.asList(a, b, c, d, e);
        List<GcObject> toSpace = new ArrayList<>();
        List<GcObject> roots = List.of(a);

        Map<GcObject, GcObject> copied = new HashMap<>();

        for (GcObject root : roots) {
            GcObject copiedRoot = copy(root, copied, toSpace);
        }

        System.out.println("=== After Copying GC ===");
        for (GcObject obj : toSpace) {
            System.out.println("[LIVE] " + obj);
        }

        for (GcObject obj : fromSpace) {
            if (!copied.containsKey(obj)) {
                System.out.println("[GC]   " + obj);
            }
        }
    }

    private static GcObject copy(GcObject original, Map<GcObject, GcObject> copied, List<GcObject> toSpace) {
        if (copied.containsKey(original)) return copied.get(original);

        GcObject newObj = new GcObject(original.toString());
        copied.put(original, newObj);
        toSpace.add(newObj);

        for (GcObject ref : original.getReferences()) {
            newObj.addReference(copy(ref, copied, toSpace));
        }

        return newObj;
    }
}
