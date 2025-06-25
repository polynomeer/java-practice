package com.polynomeer.hashcode;

import java.util.Objects;

public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        return o instanceof Person p &&
                Objects.equals(name, p.name) &&
                age == p.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
