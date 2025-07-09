package com.polynomeer.equals;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Supplier;

public class EqualsContractTester<T> {

    private final Supplier<T> supplierA;
    private final Supplier<T> supplierB;
    private final Supplier<T> supplierC;
    private final Supplier<T> supplierDifferent;

    /**
     * @param supplierA         동일한 객체 A (기준)
     * @param supplierB         A와 equals()로 같은 객체
     * @param supplierC         A, B와 equals()로 같은 객체
     * @param supplierDifferent A와 다른 객체
     */
    public EqualsContractTester(Supplier<T> supplierA,
                                Supplier<T> supplierB,
                                Supplier<T> supplierC,
                                Supplier<T> supplierDifferent) {
        this.supplierA = supplierA;
        this.supplierB = supplierB;
        this.supplierC = supplierC;
        this.supplierDifferent = supplierDifferent;
    }

    public void testEqualsContract() {
        T a = supplierA.get();
        T b = supplierB.get();
        T c = supplierC.get();
        T d = supplierDifferent.get();

        // 1. Reflexivity
        assertEquals(a, a, "Reflexivity failed: a != a");

        // 2. Symmetry
        assertEquals(a, b, "Symmetry failed: a != b");
        assertEquals(b, a, "Symmetry failed: b != a");

        // 3. Transitivity
        assertEquals(a, b, "Transitivity step 1 failed: a != b");
        assertEquals(b, c, "Transitivity step 2 failed: b != c");
        assertEquals(a, c, "Transitivity step 3 failed: a != c");

        // 4. Consistency
        for (int i = 0; i < 5; i++) {
            assertEquals(a, b, "Consistency failed on iteration " + i);
        }

        // 5. null comparison
        assertNotEquals(a, null, "Null comparison failed: a == null");

        // 6. Inequality with different object
        assertNotEquals(a, d, "Inequality failed: a == d");

        // 7. hashCode rule (optional, but strongly recommended)
        assertEquals(a.hashCode(), b.hashCode(), "HashCode contract failed: a.hashCode() != b.hashCode()");
        assertEquals(b.hashCode(), c.hashCode(), "HashCode contract failed: b.hashCode() != c.hashCode()");
    }
}
