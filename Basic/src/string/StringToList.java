package string;

import java.util.Arrays;
import java.util.List;

public class StringToList {

    public static List<String> toList(String s) {
        s = s.replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll("\"", "")
                .replaceAll(" ", "");

        String[] strings = s.split(",");
        return Arrays.asList(strings);
    }

    public static void main(String[] args) {
        String s = "[\"aaa\", \"bbb\", \"ccc\"]";
        System.out.println(s);
        List<String> list = toList(s);
        System.out.println(list);
    }
}
