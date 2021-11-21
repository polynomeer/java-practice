package effective.item9;

public class MyResource implements AutoCloseable {

    public void doSomething() throws FirstException {
        System.out.println("Doing something...");
        throw new FirstException("First Exception occurred!");
    }

    @Override
    public void close() throws SecondException {
        System.out.println("Clean my resources");
        throw new SecondException("Second Exception occurred!");
    }

    public void tryFinally() {
        MyResource myResource = null;
        try {
            myResource = new MyResource();
            myResource.doSomething();
        } finally {
            if (myResource != null) {
                myResource.close();
            }
        }
    }

    public void tryWithResources() {
        try(MyResource myResource = new MyResource()) {
            myResource.doSomething();
        }

    }

    public static void main(String[] args) {
        MyResource myResource = new MyResource();
//        myResource.tryFinally();
        myResource.tryWithResources();
    }
}
