package effective.item1;

public interface PersonInterface {

    public static Person getPerson() {
        return new Person();
    }

    // Java8에서는 public static을 추가할 수 있으므로 Collections라는 인터페이스에서
    // 여러가지 콜렉션에 대한 구현체를 가진다.
    // 하지만 Java9에서는 private static 메서드를
}
