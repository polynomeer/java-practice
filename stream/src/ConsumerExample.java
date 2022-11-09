import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerExample {
    public static void main(String[] args) {
        Consumer<String> myStringConsumer = (String str) -> {
            System.out.println("str = " + str);
        };
        myStringConsumer.accept("hello");

        List<Integer> integerInputs = Arrays.asList(4, 2, 3);
        Consumer<Integer> myIntegerProcessor = (Integer x) -> {
            System.out.println("Processing Integer " + x);
        };
        process(integerInputs, myIntegerProcessor);

        Consumer<Integer> myDifferentIntegerProcessor = (Integer x) -> {
            System.out.println("Processing Integer in different way " + x);
        };
        process(integerInputs, myDifferentIntegerProcessor);
    }

    public static void process(List<Integer> inputs, Consumer<Integer> processor) {
        for (Integer input : inputs) {
            processor.accept(input);
        }
    }
}
