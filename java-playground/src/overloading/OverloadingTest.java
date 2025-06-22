package overloading;

public class OverloadingTest {
    private String field1;
    private Integer field2;

    public OverloadingTest() {
    }

    public OverloadingTest(String field1) {
        this.field1 = field1;
    }

    public OverloadingTest(Integer field2) {
        this.field2 = field2;
    }

    public OverloadingTest(String field1, Integer field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    private Integer method() {
        return 1;
    }

    private void method(int param) {

    }

    private Integer method(int param1, int param2) {
        return 2;
    }

    private Double method(double param) {
        return 1.0;
    }


}
