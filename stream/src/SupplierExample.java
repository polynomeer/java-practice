import java.util.function.Supplier;

public class SupplierExample {
    public static void main(String[] args) {
        Supplier<String> myStringSupplier = () -> "supplier test";
        System.out.println("myStringSupplier = " + myStringSupplier.get());

        Supplier<Double> myRandomDoubleSupplier = Math::random;
        printRandomDoubles(myRandomDoubleSupplier, 5);
    }

    public static void printRandomDoubles(Supplier<Double> randomSupplier, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("randomSupplier = " + randomSupplier.get());
        }
    }
}
