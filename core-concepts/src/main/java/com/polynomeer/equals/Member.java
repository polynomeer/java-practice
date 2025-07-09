package com.polynomeer.equals;

import java.util.Objects;

public class Member {
    public String name;
    public int age;
    public String phone;
    public String email;

    public Member(String name, int age, String phone, String email) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return age == member.age &&
                Objects.equals(name, member.name) &&
                Objects.equals(phone, member.phone) &&
                Objects.equals(email, member.email);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, age, phone, email);
    }
}
