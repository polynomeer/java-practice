package com.polynomeer.hashcode;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Order {
    private final String id;
    private final List<String> items;
    private final Map<String, Integer> itemCounts;

    public Order(String id, List<String> items, Map<String, Integer> itemCounts) {
        this.id = id;
        this.items = items;
        this.itemCounts = itemCounts;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order)) return false;
        Order other = (Order) o;
        return Objects.equals(id, other.id)
                && Objects.equals(items, other.items) // List equals: 순서 고려
                && Objects.equals(itemCounts, other.itemCounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items, itemCounts);
    }
}
