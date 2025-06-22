package effective.item1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "Google", "Mozila FireFox", "Yahoo");
        List<String> list2 = Collections.unmodifiableList(list);

        System.out.println("Unmodifiable List: " + list2);

        list.add("Safari");

        System.out.print("Unmodifiable List after adding element to the list:");
        System.out.println(list2);
    }
}

// reference: https://www.javatpoint.com/java-collections-unmodifiablelist-method#:~:text=%E2%86%92%20%E2%86%90%20prev-,Java%20Collections%20unmodifiableList()%20Method,iterator%2C%20results%20in%20an%20UnsupportedOperationException.
