package com.polynomeer.hashcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class EqualsHashCodeContractTest<T> {

    protected abstract T createValueA(); // A
    protected abstract T createValueB(); // logically equal to A
    protected abstract T createDifferentValue(); // logically not equal to A

    @Test
    void equals_shouldBeReflexive() {
        T a = createValueA();
        assertEquals(a, a, "equals must be reflexive: a.equals(a)");
    }

    @Test
    void equals_shouldBeSymmetric() {
        T a = createValueA();
        T b = createValueB();
        assertEquals(a, b, "a.equals(b) failed");
        assertEquals(b, a, "b.equals(a) failed");
    }

    @Test
    void equals_shouldBeTransitive() {
        T a = createValueA();
        T b = createValueB();
        T c = createValueB(); // another logically equal object

        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c, "equals must be transitive");
    }

    @Test
    void equals_shouldReturnFalseForNull() {
        T a = createValueA();
        assertNotEquals(null, a, "equals(null) must return false");
    }

    @Test
    void equals_shouldReturnFalseForDifferentType() {
        T a = createValueA();
        assertNotEquals("not a T", a, "equals with unrelated type must return false");
    }

    @Test
    void hashCode_shouldBeConsistentWithEquals() {
        T a = createValueA();
        T b = createValueB();

        assertEquals(a, b, "Precondition failed: a.equals(b)");
        assertEquals(a.hashCode(), b.hashCode(), "hashCode must be equal for equal objects");
    }

    @Test
    void equals_shouldReturnFalseForDifferentObject() {
        T a = createValueA();
        T d = createDifferentValue();

        assertNotEquals(a, d, "Different objects should not be equal");
    }
}
