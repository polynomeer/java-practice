import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PredicateExample {
    public static void main(String[] args) {
        Predicate<Integer> isPositive = (Integer x) -> {
            return x > 0;
        };
        System.out.println("isPositive = " + isPositive.test(-10));

        List<Integer> inputs = Arrays.asList(10, -5, 4, -2, 0, 3);
        System.out.println("Positive number: " + filter(inputs, isPositive));
        System.out.println("Non-positive number: " + filter(inputs, isPositive.negate()));
        System.out.println("Non-negative number: " + filter(inputs, isPositive.or((Integer x) -> {
            return x == 0;
        })));
        System.out.println("Positive even numbers: " + filter(inputs, isPositive.and((Integer x) -> {
            return x % 2 == 0;
        })));
    }

    public static <T> List<T> filter(List<T> inputs, Predicate<T> condition) {
        List<T> output = new ArrayList<>();
        for (T input : inputs) {
            if (condition.test(input)) {
                output.add(input);
            }
        }
        return output;
    }
}
