package com.polynomeer.collection;

class HashTable {
    static class Entry {
        String key;
        int value;
        Entry next;

        Entry(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int SIZE = 16;
    private final Entry[] table = new Entry[SIZE];

    public void put(String key, int value) {
        int index = Math.abs(key.hashCode()) % SIZE;
        Entry newEntry = new Entry(key, value);

        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry current = table[index];
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                if (current.next == null) break;
                current = current.next;
            }
            current.next = newEntry;
        }
    }

    public Integer get(String key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        Entry current = table[index];
        while (current != null) {
            if (current.key.equals(key)) return current.value;
            current = current.next;
        }
        return null;
    }
}
