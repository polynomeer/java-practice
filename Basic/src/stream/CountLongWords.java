package stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Reference: https://github.com/gilbutITbook/006985
 */
public class CountLongWords {
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(
                Paths.get("alice.txt")), StandardCharsets.UTF_8);

        List<String> words = List.of(contents.split("\\PL+"));

        System.out.println(countWord(words));
        System.out.println(countWordStream(words));
        System.out.println(countWordParallelStream(words));

    }

    private static long countWordParallelStream(List<String> words) {
        return words.parallelStream().filter(w -> w.length() > 12).count();
    }

    private static long countWordStream(List<String> words) {
        return words.stream().filter(w -> w.length() > 12).count();
    }

    private static long countWord(List<String> words) {
        long count = 0;
        for (String word : words) {
            if (word.length() > 12) count++;
        }
        return count;
    }
}
