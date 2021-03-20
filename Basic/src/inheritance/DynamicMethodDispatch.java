package inheritance;

// A Java program to illustrate Dynamic Method
// Dispatch using hierarchical inheritance
class Parent {
    void say() {
        System.out.println("Parent say...");
    }
}

class FirstChild extends Parent {
    @Override
    void say() {
        System.out.println("First Child says...");
    }
}

class SecondChild extends Parent {
    @Override
    void say() {
        System.out.println("Second Child says...");
    }
}

// Driver class
class DynamicMethodDispatch {
    public static void main(String[] args) {
        Parent parent = new Parent();
        FirstChild firstChild = new FirstChild();
        SecondChild secondChild = new SecondChild();

        Parent ref;         // obtain a reference of type Parent
        ref = parent;       // ref refers to an Parent object
        ref.say();          // calling Parent's version of say()
        ref = firstChild;    // now ref refers to a FirstChild object
        ref.say();          // calling FirstChild's version of say()
        ref = secondChild;  // now ref refers to a SecondChild object
        ref.say();          // calling SecondChild's version of say()

        polymorphism(parent);
        polymorphism(firstChild);
        polymorphism(secondChild);
    }

    public static void polymorphism(Parent parent) {
        Parent ref;
        ref = parent;
        ref.say();
    }

}
