package com.polynomeer.hashcode;

import java.util.Objects;

public class KeyClasses {

    public static class GoodKey {
        private final String value;

        public GoodKey(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof GoodKey && Objects.equals(value, ((GoodKey) o).value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }

    public static class BadKey {
        private final String value;

        public BadKey(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof BadKey && Objects.equals(value, ((BadKey) o).value);
        }

        @Override
        public int hashCode() {
            return 42; // 모든 객체가 동일한 해시
        }
    }
}
