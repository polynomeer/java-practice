package advanced;

import advanced.model.Order;
import advanced.model.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToMap {

    public static void main(String[] args) {
        Map<Integer, String> numberMap = Stream.of(3, 5, -4, 2, 6)
                .collect(Collectors.toMap(x -> x, x -> "Number is " + x));
        System.out.println("numberMap = " + numberMap);
        System.out.println("number value for 3 = " + numberMap.get(3));

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

        Map<Integer, User> userIdToUserMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        System.out.println("userIdToUserMap = " + userIdToUserMap);
        System.out.println("user id 103 = " + userIdToUserMap.get(103));

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

        // Create a map from order id to order status
        Map<Long, Order.OrderStatus> collect = orders.stream()
                .collect(Collectors.toMap(Order::getId, Order::getStatus));

        System.out.println("collect = " + collect);
    }
}
