package pattern.service;

import pattern.User;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class UserServiceInFunctionalWay {
    private final Predicate<User> validateUser;
    private final Consumer<User> writeToDatabase;

    public UserServiceInFunctionalWay(Predicate<User> validateUser, Consumer<User> writeToDatabase) {
        this.validateUser = validateUser;
        this.writeToDatabase = writeToDatabase;
    }

    public void createUser(User user) {
        if (validateUser.test(user)) {
            writeToDatabase.accept(user);
        } else {
            System.out.println("Cannot create user");
        }
    }
}
