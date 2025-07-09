package com.polynomeer.equals;

import org.junit.jupiter.api.Test;

public class MemberEqualsTest {

    @Test
    void testMemberEqualsContract() {
        EqualsContractTester<Member> tester = new EqualsContractTester<>(
                () -> new Member("Alice", 30, "010-1234-5678", "alice@example.com"),
                () -> new Member("Alice", 30, "010-1234-5678", "alice@example.com"),
                () -> new Member("Alice", 30, "010-1234-5678", "alice@example.com"),
                () -> new Member("Bob", 25, "010-9999-9999", "bob@example.com")
        );

        tester.testEqualsContract();
    }
}
