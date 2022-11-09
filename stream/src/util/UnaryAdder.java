package util;

import java.util.function.UnaryOperator;

public class UnaryAdder implements UnaryOperator<Integer> {

    @Override
    public Integer apply(Integer x) {
        return x + 10;
    }
}
