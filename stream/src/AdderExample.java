import util.Adder;

import java.util.function.Function;

public class AdderExample {

    public static void main(String[] args) {
        Function<Integer, Integer> adder1 = new Adder();
        int result1 = adder1.apply(5);
        System.out.println("result1 = " + result1);

        Function<Integer,Integer> adder2 = (Integer x) -> x + 10;
        int result2 = adder2.apply(5);
        System.out.println("result2 = " + result2);
    }
}
