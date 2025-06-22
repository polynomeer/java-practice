package effective.item1;

import java.util.EnumSet;

public class Person {
    String name;
    String address;

    public Person() {
    }

    private static final Person INSTANCE = new Person();

    public Person(String name) {
        this.name = name;
    }

    static class SmallPerson extends Person {
    }

    // 시그니처가 동일해서 사용불가
//    public Person(String address) {
//        this.address = address;
//    }

    public static Person withAddress(String address) {
        Person foo = new Person();
        foo.address = address;
        return foo;
    }

    public static Person withName(String name) {
        return new Person(name);
    }

    public static Person getPerson() {
        return INSTANCE;
    }
    // 정적 팩토리 메서드의 유연성 -> 입력 매개변수에 따라 다를 수 있다.
    public static Person getPerson(boolean flag) {
        return flag ? new Person() : new SmallPerson();
    }

    public static void main(String[] args) {
        Person august = new Person("august");
        Person foo = Person.withName("foo");

        EnumSet<Color> colors = EnumSet.allOf(Color.class);
//        EnumSet<Color> twoColors = EnumSet.of(BLUE, WHITE);
    }

    enum Color {
        RED, BLUE, WHITE, BLACK
    }

    // private static 메서드의 필요성 -> 스코프
    public static void doSomething() {
        // TODO 스터디를 한다.
        // TODO 점심 약속에 간다.
        // TODO 서울로 돌아간다.
        맥주한잔하고잔다();
    }

    public static void doSomethingTomorrow() {
        // TODO 이삿짐을 옮긴다.
        // TODO 집을 정리한다.
        맥주한잔하고잔다();
    }

    private static void 맥주한잔하고잔다() {
        // TODO 맥주 한 잔 한다.
        // TODO 잔다.
    }
}
