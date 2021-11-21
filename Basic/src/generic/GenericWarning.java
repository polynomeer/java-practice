package generic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GenericWarning {
    private final int size = 10;
    private final Object[] elements = new Object[0];

    public static void main(String[] args) {
        Set<String> test = new HashSet<>();

    }

    public <T> T[] toArray1(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    public <T> T[] toArray2(T[] a) {
        if (a.length < size) {
            @SuppressWarnings("unchecked") T[] result =
                    (T[]) Arrays.copyOf(elements, size, a.getClass());
            return result;
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }
}
