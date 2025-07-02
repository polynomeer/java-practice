package com.polynomeer.gc;

import java.util.*;

public class G1Gc {
    private final List<Region> heap = new ArrayList<>();
    private final List<GcObject> roots = new ArrayList<>();
    private int regionIdCounter = 0;

    public Region createRegion(Region.Type type) {
        Region region = new Region("R" + regionIdCounter++, type);
        heap.add(region);
        return region;
    }

    public void addRoot(GcObject obj) {
        roots.add(obj);
    }

    public void minorGc() {
        System.out.println("[Minor GC] Young 영역만 수집");
        mark();
        for (Region r : heap) {
            if (r.getType() == Region.Type.YOUNG) {
                r.sweepUnmarked();
            } else {
                for (GcObject obj : r.getObjects()) {
                    obj.unmark(); // Old는 유지
                }
            }
        }
        printHeap();
    }

    public void mixedGc() {
        System.out.println("[Mixed GC] Young + 일부 Old 수집");
        mark();
        for (Region r : heap) {
            if (r.getType() == Region.Type.YOUNG || shouldCollectOld(r)) {
                r.sweepUnmarked();
            } else {
                for (GcObject obj : r.getObjects()) {
                    obj.unmark();
                }
            }
        }
        printHeap();
    }

    private void mark() {
        for (GcObject root : roots) {
            root.mark();
        }
    }

    private boolean shouldCollectOld(Region r) {
        long live = r.getObjects().stream().filter(GcObject::isMarked).count();
        return live < r.getObjects().size() / 2; // 일부만 살아있으면 수거
    }

    public void printHeap() {
        System.out.println("=== 힙 상태 ===");
        for (Region r : heap) {
            System.out.println(r);
        }
        System.out.println("================");
    }
}
