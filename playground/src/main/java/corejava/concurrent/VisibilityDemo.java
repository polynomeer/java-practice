package corejava.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VisibilityDemo {
    private static volatile boolean done = false; // volatile 제어자를 추가하면 문제가 해결된다.

    public static void main(String[] args) {
        Runnable hellos = () -> {
            for (int i = 1; i <= 1000; i++)
                System.out.println("Hello " + i);
            done = true; // 이 코드의 효력이 보이지 않을 수 있다.
        };
        Runnable goodbye = () -> {
            int i = 1;
            while (!done) i++; // Hello 1000이 되면 done = true 가 될까?
            System.out.println("Goodbye " + i);
        };
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(hellos);
        executor.execute(goodbye);
    }
}