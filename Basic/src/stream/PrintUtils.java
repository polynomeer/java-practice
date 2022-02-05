package stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintUtils {
    /**
     * 스트림 요소를 출력하는 메서드
     * 최대 10개 요소까지만 출력하도록 제한한다.
     *
     * @param title  출력 제목
     * @param stream 출력 요소 스트림
     * @param <T>    출력 요소의 제네릭 타입
     */
    public static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE + 1).collect(Collectors.toList());
        System.out.print(title + ": ");
        if (firstElements.size() <= SIZE)
            System.out.println(firstElements);
        else {
            firstElements.remove(SIZE);
            String out = firstElements.toString();
            System.out.println(out.substring(0, out.length() - 1) + ", ...]");
        }
    }
}
