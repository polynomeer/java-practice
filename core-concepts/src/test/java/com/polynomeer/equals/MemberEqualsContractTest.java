package com.polynomeer.equals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemberEqualsContractTest {
    Member a = new Member("Alice", 30, "010-1234-5678", "alice@example.com");
    Member b = new Member("Alice", 30, "010-1234-5678", "alice@example.com");
    Member c = new Member("Alice", 30, "010-1234-5678", "alice@example.com");
    Member d = new Member("Bob", 25, "010-0000-0000", "bob@example.com");

    @Test
    void testReflexivity() {
        assertEquals(a, a);
    }

    @Test
    void testSymmetry() {
        assertEquals(a, b);
        assertEquals(b, a);
    }

    @Test
    void testTransitivity() {
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);
    }

    @Test
    void testConsistency() {
        for (int i = 0; i < 10; i++) {
            assertEquals(a, b);
        }
    }

    @Test
    void testNullHandling() {
        assertNotEquals(a, null);
    }

    @Test
    void testInequality() {
        assertNotEquals(a, d);
    }
}
