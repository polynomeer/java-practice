package functional;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class MethodReferenceExample {
    public static void main(String[] args) {
        Function<String, Integer> strToInt = Integer::parseInt;
        System.out.println(strToInt.apply("20"));

        String str = "hello";
        Predicate<String> equalsToHello = str::equals;
        System.out.println(equalsToHello.test("hello"));
        System.out.println(equalsToHello.test("world"));

        /*
         * static method를 Method Reference로 호출
         */
        System.out.println(calculate(8, 2, (x, y) -> x + y));
        System.out.println(calculate(8, 2, MethodReferenceExample::multiply));

        /*
         * instance method를 Method Reference로 호출
         */
        MethodReferenceExample instance = new MethodReferenceExample();
        System.out.println(calculate(8, 2, instance::subtract));

        /*
         * instance method를 this 지시자를 이용한 Method Reference로 호출
       출 */
        instance.myMethod();
    }

    public static int calculate(int x, int y, BiFunction<Integer, Integer, Integer> operator) {
        return operator.apply(x, y);
    }

    public static int multiply(int x, int y) {
        return x * y;
    }

    public int subtract(int x, int y) {
        return x - y;
    }

    public void myMethod() {
        System.out.println(calculate(10, 3, this::subtract));
    }
}
