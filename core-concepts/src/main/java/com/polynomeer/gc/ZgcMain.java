package com.polynomeer.gc;

public class ZgcMain {
    public static void main(String[] args) {
        ZgcSimulator zgc = new ZgcSimulator();

        ZgcObject a = zgc.allocate("A");
        ZgcObject b = zgc.allocate("B");
        ZgcObject c = zgc.allocate("C");
        ZgcObject d = zgc.allocate("D"); // D is unreachable
        ZgcObject e = zgc.allocate("E");

        a.addReference(b);
        b.addReference(c);
        c.addReference(e);

        zgc.addRoot(a); // A is GC root

        zgc.runGc(); // D should be removed (not relocated)
    }
}
