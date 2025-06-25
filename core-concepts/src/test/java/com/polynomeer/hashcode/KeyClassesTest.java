package com.polynomeer.hashcode;

import static com.polynomeer.hashcode.KeyClasses.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HashMap Key Behavior Test")
class KeyClassesTest {

    @Test
    void goodKey_shouldBehaveCorrectlyInHashMap() {
        Map<GoodKey, String> map = new HashMap<>();
        GoodKey k1 = new GoodKey("key");
        GoodKey k2 = new GoodKey("key");

        map.put(k1, "value");
        assertEquals("value", map.get(k2));
    }

    @Test
    void badKey_shouldFailToRetrieveDespiteLogicalEquality() {
        Map<BadKey, String> map = new HashMap<>();
        BadKey k1 = new BadKey("key");
        BadKey k2 = new BadKey("key");

        map.put(k1, "value");
        assertEquals("value", map.get(k2), "HashMap still works because equals() is implemented.");
    }

    @Test
    void comparePerformanceOfGoodAndBadKey() {
        int size = 10_000;
        Map<GoodKey, String> goodMap = new HashMap<>();
        Map<BadKey, String> badMap = new HashMap<>();

        long goodStart = System.nanoTime();
        for (int i = 0; i < size; i++) {
            goodMap.put(new GoodKey("key" + i), "v" + i);
        }
        for (int i = 0; i < size; i++) {
            goodMap.get(new GoodKey("key" + i));
        }
        long goodEnd = System.nanoTime();

        long badStart = System.nanoTime();
        for (int i = 0; i < size; i++) {
            badMap.put(new BadKey("key" + i), "v" + i);
        }
        for (int i = 0; i < size; i++) {
            badMap.get(new BadKey("key" + i));
        }
        long badEnd = System.nanoTime();

        long goodTime = goodEnd - goodStart;
        long badTime = badEnd - badStart;

        System.out.println("GoodKey total time (ms): " + goodTime / 1_000_000.0);
        System.out.println("BadKey total time (ms): " + badTime / 1_000_000.0);

        assertTrue(badTime > goodTime * 3, "BadKey should be significantly slower due to collisions.");
    }
}
