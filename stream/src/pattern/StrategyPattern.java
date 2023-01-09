package pattern;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class StrategyPattern {

    public static void main(String[] args) {
        User user1 = User.builder(1, "Alice")
                .with(builder -> {
                    builder.emailAddress = "alice@gmail.com";
                    builder.isVerified = false;
                    builder.createdAt = LocalDateTime.now();
                    builder.friendUserIds = Arrays.asList(201, 202, 203, 204, 211, 212, 213, 214);
                }).build();

        User user2 = User.builder(2, "Bob")
                .with(builder -> {
                    builder.emailAddress = "bob@gmail.com";
                    builder.isVerified = true;
                    builder.createdAt = LocalDateTime.now().minusHours(2);
                    builder.friendUserIds = Arrays.asList(212, 213, 214);
                }).build();

        User user3 = User.builder(3, "Charlie")
                .with(builder -> {
                    builder.emailAddress = "charlie@gmail.com";
                    builder.isVerified = true;
                    builder.createdAt = LocalDateTime.now().plusHours(5);
                    builder.friendUserIds = Arrays.asList(201, 202, 203, 204, 211, 212);
                }).build();
        List<User> users = Arrays.asList(user1, user2, user3);

        EmailSender emailSender = new EmailSender();
        EmailProvider verifyYourEmailAddressEmailProvider = new VerifyYourEmailAddressEmailProvider();
        EmailProvider makeMoreFriendsEmailProvider = new MakeMoreFriendsEmailProvider();

        emailSender.setEmailProvider(verifyYourEmailAddressEmailProvider);
        users.stream()
                .filter(user -> !user.isVerified())
                .forEach(emailSender::sendEmail);

        emailSender.setEmailProvider(makeMoreFriendsEmailProvider);
        users.stream()
                .filter(User::isVerified)
                .filter(user -> user.getFriendUserIds().size() <= 5)
                .forEach(emailSender::sendEmail);

        emailSender.setEmailProvider(user -> "'Play With Friends' email for " + user.getName());
        users.stream()
                .filter(User::isVerified)
                .filter(user -> user.getFriendUserIds().size() > 5)
                .forEach(emailSender::sendEmail);

    }
}
