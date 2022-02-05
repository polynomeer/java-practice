package stream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static stream.PrintUtils.show;

/**
 * Collection::stream 으로 스트림으로 변환이 가능하다.
 * 하지만 배열은 Stream::of로 얻어내야 한다.
 * Reference: https://github.com/gilbutITbook/006985
 */
public class CreatingStreams {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("alice.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        // Stream::of 메서드는 가변인수 매개변수를 받는다.
        // String::split 메서드는 String[] 배열을 리턴한다.
        Stream<String> words = Stream.of(contents.split("\\PL+"));
        show("words", words);

        // 여러개의 String 인수로 스트림 생성
        Stream<String> song = Stream.of("gently", "down", "the", "stream");
        show("song", song);

        // 배열의 일부에서 스트림 생성
        String[] strArr = new String[]{"gently", "down", "the", "stream"};
        Stream<String> arrayStream = Arrays.stream(strArr, 1, 2);
        show("array part", arrayStream);

        // 요소가 없는 스트림 생성
        Stream<String> silence = Stream.empty();
        show("silence", silence);

        // Stream::generate로 무한 스트림을 생성 - Supplier<T>
        Stream<String> echos = Stream.generate(() -> "Echo");
        show("echos", echos);

        Stream<Double> randoms = Stream.generate(Math::random);
        show("randoms", randoms);

        // Stream::iterate로 무한 스트림을 생성 - UnaryOperator<T>
        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE));
        show("integers", integers);

        // Stream::iterate로 유한 스트림 생성 - UnaryOperator<T>
        BigInteger limit = new BigInteger("7");
        integers = Stream.iterate(BigInteger.ZERO,
                n -> n.compareTo(limit) < 0,
                n -> n.add(BigInteger.ONE));
        show("integers", integers);

        // Files::lines는 파일에 들어있는 모든 행을 포함하는 Stream을 리턴한다.
        Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
        show("wordsAnotherWay", wordsAnotherWay);

        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            show("lines", lines);
        }
    }
}
