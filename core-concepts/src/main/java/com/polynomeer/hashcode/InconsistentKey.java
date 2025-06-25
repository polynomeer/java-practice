package com.polynomeer.hashcode;

public class InconsistentKey {
    private final String value;

    public InconsistentKey(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof InconsistentKey && value.equals(((InconsistentKey) o).value);
    }

    @Override
    public int hashCode() {
        return value.length(); // 너무 단순한 hashCode → 충돌 유발
    }

    @Override
    public String toString() {
        return "Key[" + value + "]";
    }
}
