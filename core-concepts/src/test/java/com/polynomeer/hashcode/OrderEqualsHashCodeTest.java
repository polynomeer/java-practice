package com.polynomeer.hashcode;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderEqualsHashCodeTest extends EqualsHashCodeContractTest<Order> {

    @Override
    protected Order createValueA() {
        return new Order("order-1", List.of("apple", "banana"), Map.of("apple", 2, "banana", 3));
    }

    @Override
    protected Order createValueB() {
        return new Order("order-1", List.of("apple", "banana"), Map.of("apple", 2, "banana", 3));
    }

    @Override
    protected Order createDifferentValue() {
        return new Order("order-2", List.of("banana", "apple"), Map.of("banana", 1));
    }

    @Test
    void listOrderMattersInEquals() {
        Order o1 = new Order("x", List.of("a", "b"), Map.of());
        Order o2 = new Order("x", List.of("b", "a"), Map.of());

        // List의 equals는 순서에 민감
        assertNotEquals(o1, o2, "List 순서가 다르면 equals도 false여야 한다");
    }

    @Test
    void mapOrderDoesNotMatterInEquals() {
        Order o1 = new Order("x", List.of(), Map.of("a", 1, "b", 2));
        Order o2 = new Order("x", List.of(), Map.of("b", 2, "a", 1));

        // Map의 equals는 순서에 민감하지 않음
        assertEquals(o1, o2, "Map 순서가 달라도 equals는 true여야 한다");
        assertEquals(o1.hashCode(), o2.hashCode(), "Map 순서 달라도 hashCode는 같아야 한다");
    }
}
