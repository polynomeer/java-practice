package functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static functional.TestClass.Color.*;

public class TestClass {

    enum Color {
        GREEN, RED
    }


    static class Apple {
        private Color color;
        private int weight;

        public Color getColor() {
            return color;
        }

        public int getWeight() {
            return weight;
        }

        public static boolean isGreenApple(Apple apple) {
            return GREEN.equals(apple.getColor());
        }

        public static boolean isHeavyApple(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    public static void main(String[] args) {
//        File[] hiddenFiles = new File(".").listFiles(File::isHidden);
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }


    static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    static void useCase(List<Apple> inventory) {
        filterApples(inventory, Apple::isGreenApple);
        filterApples(inventory, Apple::isHeavyApple);
    }

}




