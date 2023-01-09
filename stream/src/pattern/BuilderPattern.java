package pattern;

import java.time.LocalDateTime;

public class BuilderPattern {

    public static void main(String[] args) {
        User user = User.builder(1, "Alice")
                .with(builder -> {
                    builder.emailAddress = "alice@gmail.com";
                    builder.isVerified = true;
                    builder.createdAt = LocalDateTime.now();
                }).build();

        System.out.println("user = " + user);
    }
}
