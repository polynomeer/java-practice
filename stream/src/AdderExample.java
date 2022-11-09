import util.Adder;

import java.util.function.Function;

public class AdderExample {

    public static void main(String[] args) {
        Function<Integer, Integer> adder = new Adder();
        int result = adder.apply(5);
        System.out.println("result = " + result);
    }
}
