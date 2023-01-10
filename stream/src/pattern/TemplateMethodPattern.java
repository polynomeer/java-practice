package pattern;

import pattern.service.InternalUserService;
import pattern.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TemplateMethodPattern {

    public static void main(String[] args) {
        User user = User.builder(1, "Alice")
                .with(builder -> {
//                    builder.emailAddress = "alice@gmail.com";
                    builder.isVerified = false;
                    builder.createdAt = LocalDateTime.now();
                    builder.friendUserIds = Arrays.asList(201, 202, 203, 204, 211, 212, 213, 214);
                }).build();

        UserService userService = new UserService();
        InternalUserService internalUserService = new InternalUserService();

//        userService.createUser(user);
        internalUserService.createUser(user);
    }
}
