package advanced;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsCollect {

    public static void main(String[] args) {
        List<Integer> numberList = Stream.of(3, 5, -3, 3, 4, 5)
                .collect(Collectors.toList());
        System.out.println("numberList = " + numberList);

        Set<Integer> numberSet = Stream.of(3, 5, -3, 3, 4, 5)
                .collect(Collectors.toSet());
        System.out.println("numberSet = " + numberSet);

        List<Integer> numbers = Stream.of(3, 5, -3, 3, 4, 5)
                .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toList()));
        System.out.println("numbers = " + numbers);

        Integer sum = Stream.of(3, 5, -3, 3, 4, 5)
                .collect(Collectors.reducing(0, (x, y) -> x + y));
        System.out.println("sum = " + sum);
    }
}
