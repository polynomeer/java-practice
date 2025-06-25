package com.polynomeer.hashcode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("equals() and hashCode() mismatch test")
public class InconsistentKeyTest {

    @Test
    void hashSet_shouldFailToPreventDuplicates_ifHashCodeIsInconsistent() {
        Set<InconsistentKey> set = new HashSet<>();

        InconsistentKey key1 = new InconsistentKey("AUGUST");
        InconsistentKey key2 = new InconsistentKey("AUGUST");

        assertTrue(key1.equals(key2), "equals() returns true");
        assertEquals(key1.hashCode(), key2.hashCode(), "hashCode should be equal if equals is true");

        set.add(key1);
        boolean contains = set.contains(key2);

        // ❌ 예상과 다르게 false가 나올 수 있음
        assertTrue(contains, "HashSet should contain logically equal key");
    }

    @Test
    void hashMap_shouldFailToRetrieveValue_ifHashCodeIsInconsistent() {
        Map<InconsistentKey, String> map = new HashMap<>();

        InconsistentKey key1 = new InconsistentKey("AUGUST");
        InconsistentKey key2 = new InconsistentKey("AUGUST");

        map.put(key1, "developer");
        String value = map.get(key2);

        // ❌ value == null 이면 equals/hashCode 불일치
        assertEquals("developer", value, "HashMap should retrieve value for logically equal key");
    }

    @Test
    void debugHashCodeMismatch() {
        InconsistentKey a = new InconsistentKey("AB");
        InconsistentKey b = new InconsistentKey("AB");

        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("a.hashCode(): " + a.hashCode());
        System.out.println("b.hashCode(): " + b.hashCode());
    }
}
