import util.Adder;
import util.TriFunction;
import util.UnaryAdder;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class AdderExample {

    public static void main(String[] args) {
        Function<Integer, Integer> adder1 = new Adder();
        int result1 = adder1.apply(5);
        System.out.println("result1 = " + result1);

        Function<Integer, Integer> adder2 = (Integer x) -> x + 10;
        int result2 = adder2.apply(5);
        System.out.println("result2 = " + result2);

        BiFunction<Integer, Integer, Integer> add = Integer::sum;
        int result3 = add.apply(3, 5);
        System.out.println("result3 = " + result3);

        TriFunction<Integer, Integer, Integer, Integer> addThreeNumbers =
                (Integer x, Integer y, Integer z) -> x + y + z;
        int result4 = addThreeNumbers.apply(3, 2, 5);
        System.out.println("result4 = " + result4);

        UnaryOperator<Integer> unaryAdder = new UnaryAdder();
        int result5 = unaryAdder.apply(5);
        System.out.println("result5 = " + result5);
    }
}
