package com.polynomeer.gc;

public class G1GcMain {
    public static void main(String[] args) {
        G1Gc gc = new G1Gc();

        Region young1 = gc.createRegion(Region.Type.YOUNG);
        Region old1 = gc.createRegion(Region.Type.OLD);
        Region old2 = gc.createRegion(Region.Type.OLD);

        GcObject a = new GcObject("A");
        GcObject b = new GcObject("B");
        GcObject c = new GcObject("C");
        GcObject d = new GcObject("D");
        GcObject e = new GcObject("E");

        a.addReference(b);
        b.addReference(c);

        young1.addObject(a);
        young1.addObject(b);
        old1.addObject(c);
        old2.addObject(d); // unreachable
        old2.addObject(e); // unreachable

        gc.addRoot(a);

        gc.minorGc();  // D, E는 남아있음
        gc.mixedGc();  // Old 중 불필요한 Region도 수거
    }
}
