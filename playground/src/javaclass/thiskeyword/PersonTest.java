package javaclass.thiskeyword;

public class PersonTest {
    public static void main(String[] args) {
        Person person1 = new Person("Jacob", 20, "developer");
        System.out.println(person1);
        Person person2 = new Person("Brad", 40);
        System.out.println(person2);
        Person person3 = new Person("Joseph");
        System.out.println(person3);
    }
}
