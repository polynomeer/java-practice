package thisisjava.chap6.sec15;

public class Service {
    @PrintAnnotation // default
    public void method1() {
        System.out.println("test case1");
    }

    @PrintAnnotation("*")
    public void method2() { // "-" 대신에 "*" 사용
        System.out.println("test case2");
    }

    @PrintAnnotation(value = "#", number = 20) // "-" 대신에 "#"사용, 15회 말고 20회 반복
    public void method3() {
        System.out.println("test case3");
    }
}
