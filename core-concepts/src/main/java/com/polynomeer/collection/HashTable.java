package com.polynomeer.collection;

import java.util.Objects;

/**
 * 간단한 제네릭 해시 테이블 구현.
 * <p>
 * 내부적으로 배열과 체이닝(연결 리스트)을 사용하여 키-값 쌍을 저장합니다.
 * 충돌은 체이닝 방식으로 해결하며, 로드 팩터를 기준으로 자동 리사이징을 수행합니다.
 *
 * @param <K> 키 타입
 * @param <V> 값 타입
 */
public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public HashTable() {
        table = new Entry[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }

    static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 지정된 키-값 쌍을 해시 테이블에 저장합니다.
     * <p>
     * 만약 동일한 키가 이미 존재하면, 기존 값을 새로운 값으로 덮어씁니다.
     * 키에 대한 해시를 계산하여 해당 인덱스에 데이터를 저장하며,
     * 충돌이 발생할 경우 체이닝(연결 리스트)을 통해 해결합니다.
     * <p>
     * 저장된 엔트리 수가 임계치(threshold)를 초과하면 내부 배열을 자동으로 확장(resize)합니다.
     *
     * @param key   저장할 키. null도 허용됩니다.
     * @param value 키에 대응되는 값
     */
    public void put(K key, V value) {
        int index = index(key);               // key에 대한 해시 인덱스를 계산
        Entry<K, V> head = table[index];      // 해당 인덱스에 연결된 리스트의 첫 노드를 가져옴

        // 동일한 키가 이미 존재하면 값을 덮어쓰기
        for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
            if (Objects.equals(curr.key, key)) {
                curr.value = value;
                return;
            }
        }

        // 새 엔트리를 리스트 맨 앞에 삽입 (체이닝)
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = head;
        table[index] = newEntry;
        size++;

        // 저장된 데이터 수가 임계치를 넘으면 resize
        if (size >= threshold) {
            resize();
        }
    }

    /**
     * 지정된 키에 해당하는 값을 반환합니다.
     * <p>
     * 내부적으로 키의 해시를 통해 인덱스를 계산하고,
     * 해당 인덱스에 저장된 연결 리스트를 순회하면서
     * 동일한 키를 찾습니다.
     *
     * @param key 검색할 키. null도 허용됩니다.
     * @return 해당 키에 대응되는 값, 존재하지 않으면 null
     */
    public V get(K key) {
        int index = index(key);  // key에 대한 해시 인덱스를 계산
        for (Entry<K, V> curr = table[index]; curr != null; curr = curr.next) {
            if (Objects.equals(curr.key, key)) {
                return curr.value; // 일치하는 키 발견 시 값 반환
            }
        }
        return null; // 키가 없으면 null 반환
    }

    /**
     * 지정된 키가 해시 테이블에 존재하는지 확인합니다.
     *
     * @param key 존재 여부를 확인할 키
     * @return 존재하면 true, 없으면 false
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * 지정된 키-값 쌍을 해시 테이블에서 제거합니다.
     *
     * @param key 삭제할 키
     */
    public void remove(K key) {
        int index = index(key);
        Entry<K, V> prev = null;
        Entry<K, V> curr = table[index];

        while (curr != null) {
            if (Objects.equals(curr.key, key)) {
                if (prev == null) {
                    table[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    }

    /**
     * 현재 해시 테이블에 저장된 키-값 쌍의 수를 반환합니다.
     *
     * @return 저장된 항목 수
     */
    public int size() {
        return size;
    }

    /**
     * 키의 해시코드를 기반으로 인덱스를 계산합니다.
     *
     * @param key 인덱스를 계산할 키
     * @return 내부 배열(table)의 인덱스 값
     */
    private int index(K key) {
        return (key == null ? 0 : Math.abs(key.hashCode())) % table.length;
    }

    /**
     * 해시 테이블의 크기를 두 배로 확장하고,
     * 기존 항목들을 새로운 테이블로 재해시(rehash)합니다.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        threshold = (int) (table.length * LOAD_FACTOR);
        size = 0;

        for (Entry<K, V> head : oldTable) {
            while (head != null) {
                put(head.key, head.value); // rehash
                head = head.next;
            }
        }
    }
}
