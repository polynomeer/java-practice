package pattern;

import java.time.LocalDateTime;

public class BuilderPattern {

    public static void main(String[] args) {
        User user = User.builder(1, "Alice")
                .withEmailAddress("alice@gmail.com")
                .withVerified(true)
                .withCreatedAt(LocalDateTime.now())
                .build();

        System.out.println("user = " + user);
    }
}
