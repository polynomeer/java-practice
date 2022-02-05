package stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static stream.PrintUtils.show;

public class OtherTransformations {

    public static void main(String[] args) throws IOException {
        Stream<String> uniqueWords = Stream.of("merrily", "merrily", "merrily", "gently")
                .distinct();
        show("uniqueWords", uniqueWords);

        String contents = new String(Files.readAllBytes(Paths.get("alice.txt")),
                StandardCharsets.UTF_8);
        List<String> words = List.of(contents.split("\\PL+"));
        show("words", words.stream());

        Stream<String> distinct =  words.stream().distinct();
        show("distinct", distinct);

        Stream<String> sorted =  words.stream().sorted();
        show("sorted", sorted);

        Stream<String> distinctSorted =  words.stream().distinct().sorted();
        show("distinctSorted", distinctSorted);

        Stream<String> longestFirst =  words.stream().sorted(Comparator.comparing(String::length).reversed());
        show("longestFirst", longestFirst);

        Object[] powers = Stream.iterate(1.0, p -> p * 2)
                .peek(e -> System.out.println("Fetching " + e)).limit(20).toArray();
        System.out.println(Arrays.toString(powers));
    }
}
