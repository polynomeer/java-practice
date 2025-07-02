package com.polynomeer.gc;

import java.util.*;

public class GcObjectGraphVisualizer {

    public static void visualize(List<GcObject> heap) {
        Set<GcObject> visited = new HashSet<>();
        for (GcObject obj : heap) {
            if (!visited.contains(obj)) {
                dfsPrint(obj, "", visited);
            }
        }
    }

    private static void dfsPrint(GcObject obj, String indent, Set<GcObject> visited) {
        if (!visited.add(obj)) {
            System.out.println(indent + "↳ " + obj + " (↻ already shown)");
            return;
        }

        String status = obj.isMarked() ? "✅" : "❌";
        System.out.println(indent + "↳ " + obj + " " + status);

        for (GcObject ref : obj.getReferences()) {
            dfsPrint(ref, indent + "   ", visited);
        }
    }
}
