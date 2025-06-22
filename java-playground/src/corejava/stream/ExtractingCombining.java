package corejava.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static corejava.stream.PrintUtils.show;

public class ExtractingCombining {

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
        Stream<Double> randoms = Stream.generate(Math::random).limit(5);
        show("randoms", randoms);

        String contents = "   Hello, World!   ";
        Stream<String> words = Stream.of(contents.split("\\PL+")).skip(1);
        show("words", words);

        String str = "123 Main Street";
        Stream<String> initialDigits = codePoints(str).takeWhile(
                s -> "0123456789".contains(s));
        show("initialDigits", initialDigits);
        str = "   Hello   ";
        Stream<String> withoutInitialWhiteSpace = codePoints(str).dropWhile(
                s -> s.trim().length() == 0);
        show("withoutInitialWhiteSpace", withoutInitialWhiteSpace);

        Stream<String> combined = Stream.concat(codePoints("Hello"),
                codePoints("Cat\uD83D\uDE3B"));
        show("combined", combined);
    }
}
