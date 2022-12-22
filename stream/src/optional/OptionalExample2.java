package optional;

import java.util.Optional;

public class OptionalExample2 {

    public static void main(String[] args) {
        Optional<User> maybeUser = Optional.ofNullable(maybeGetUser(false));
        maybeUser.ifPresent(user -> System.out.println(user));

        Optional<Integer> maybeId = Optional.ofNullable(maybeGetUser(false))
                .map(user -> user.getId());
        maybeId.ifPresent(System.out::println);

        String userName = Optional.ofNullable(maybeGetUser(true))
                .map(User::getName)
                .map(name -> "The name is " + name)
                .orElse("Name is empty");
        System.out.println("userName = " + userName);

        Optional<String> maybeEmail = Optional.ofNullable(maybeGetUser(true))
                .flatMap(User::getEmailAddress);
        maybeEmail.ifPresent(System.out::println);
    }

    public static User maybeGetUser(boolean returnUser) {
        if (returnUser) {
            return new User()
                    .setId(1001)
                    .setName("Alice")
                    .setEmailAddress("alice@gmail.com")
                    .setVerified(false);
        }
        return null;
    }
}
