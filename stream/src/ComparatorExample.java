import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparatorExample {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        users.add(new User(3, "Alice"));
        users.add(new User(1, "Charlie"));
        users.add(new User(5, "Bob"));
        System.out.println(users);

        Comparator<User> idComparator = Comparator.comparingInt(User::getId);
        users.sort(idComparator);
        System.out.println(users);

        users.sort(Comparator.comparing(User::getName));
        System.out.println(users);
    }
}
