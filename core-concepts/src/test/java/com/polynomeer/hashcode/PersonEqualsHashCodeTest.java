package com.polynomeer.hashcode;

public class PersonEqualsHashCodeTest extends EqualsHashCodeContractTest<Person> {

    @Override
    protected Person createValueA() {
        return new Person("August", 30);
    }

    @Override
    protected Person createValueB() {
        return new Person("August", 30); // logically equal to A
    }

    @Override
    protected Person createDifferentValue() {
        return new Person("Other", 99);
    }
}
