package stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static stream.PrintUtils.show;

/**
 * Stream 변환은 다른 스트림에 들어있는 요소에서 파생한 요소의 스트림을 만들어낸다.
 * Reference: https://github.com/gilbutITbook/006985
 */
public class FilterMapDemo {

    public static Stream<String> codePoints(String s) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            int j = s.offsetByCodePoints(i, 1);
            result.add(s.substring(i, j));
            i = j;
        }
        return result.stream();
    }

    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("alice.txt")),
                StandardCharsets.UTF_8);
        List<String> words = List.of(contents.split("\\PL+"));

        // Stream::filter : 특정 조건과 일치하는 용소로 구성된 새 스트림을 돌려준다.
        // filter 메서드의 인수는 Predicate<T> 로, T를 받아서 boolean 을 돌려준다.
        // 다음 코드는 문자열 스트림을 길이 12가 넘는 문자열로 구성된 또 다른 스트림으로 변환한다.
        Stream<String> longWords = words.stream().filter(w -> w.length() > 12);
        show("longWords", longWords);

        // Stream::map : 스트림에 들어있는 값을 모두 특정 방식으로 변환한다.
        // 다음 코드는 모든 단어를 소문자로 변환한다.
        Stream<String> lowercaseWords = words.stream().map(String::toLowerCase);
        show("lowercaseWords", lowercaseWords);

        String[] song = {"row", "row", "row", "your", "boat", "gently", "down",
                "the", "stream"};

        // 배열 song 으로부터 스트림을 얻어서 -> 길이가 0 이상인 요소에 대해서만 -> 첫글자를 잘라서 가져온다.
        Stream<String> firstChars = Stream.of(song).filter(w -> w.length() > 0).map(s -> s.substring(0, 1));
        show("firstChars", firstChars);

        // 배열 song 으로부터 스트림을 얻어서 -> 각 요소에 대하여 codePoints 를 호출한 뒤 -> 결과들을 펼친다.
        Stream<String> letters = Stream.of(song).flatMap(w -> codePoints(w));
        show("letters", letters);
    }
}
