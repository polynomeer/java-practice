package advanced;

import advanced.model.Order;
import advanced.model.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class AllMatchAnyMatch {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, -4, 2, 7, 9);

        boolean allPositive = numbers.stream()
                .allMatch(number -> number > 0);
        System.out.println("Are all numbers positive: " + allPositive);

        boolean anyNegative = numbers.stream()
                .anyMatch(number -> number < 0);
        System.out.println("Is any number negative: " + anyNegative);

        User user1 = new User()
                .setId(101)
                .setName("Alice")
                .setVerified(true)
                .setEmailAddress("alice@gmail.com");

        User user2 = new User()
                .setId(102)
                .setName("Bob")
                .setVerified(false)
                .setEmailAddress("bob@gmail.com");

        User user3 = new User()
                .setId(103)
                .setName("Charlie")
                .setVerified(false)
                .setEmailAddress("charlie@gmail.com");
        List<User> users = Arrays.asList(user1, user2, user3);

        boolean areAllUserVerified = users.stream()
                .allMatch(User::isVerified);
        System.out.println("areAllUserVerified = " + areAllUserVerified);

        Order order1 = new Order()
                .setId(1001)
                .setAmount(BigDecimal.valueOf(2000))
                .setStatus(Order.OrderStatus.CREATED)
                .setCreatedByUserId(101);

        Order order2 = new Order()
                .setId(1002)
                .setAmount(BigDecimal.valueOf(4000))
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(103);

        Order order3 = new Order()
                .setId(1003)
                .setAmount(BigDecimal.valueOf(7000))
                .setStatus(Order.OrderStatus.PROCESSED)
                .setCreatedByUserId(102);

        Order order4 = new Order()
                .setId(1004)
                .setAmount(BigDecimal.valueOf(6000))
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(104);
        List<Order> orders = Arrays.asList(order1, order2, order3, order4);

        // Check if any of orders is in ERROR status
        boolean isAnyOrderInErrorStatus = orders.stream()
                .anyMatch(order -> order.getStatus() == Order.OrderStatus.ERROR);
        System.out.println("isAnyOrderInErrorStatus = " + isAnyOrderInErrorStatus);
    }
}
