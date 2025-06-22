package javaclass.thiskeyword;

public class Person {
    String name;
    int age;
    String job;

    public Person(String name) {
        this(name, 20, "unemployed");
    }

    public Person(String name, int age) {
        this(name, age, "unemployed");
    }

    public Person(String name, int age, String job) {
//        name = name;
//        age = age;
        this.name = name;
        this.age = age;
        this.job = job;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                '}';
    }
}
