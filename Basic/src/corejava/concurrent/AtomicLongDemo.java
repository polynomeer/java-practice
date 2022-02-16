package corejava.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongDemo {
    // java.util.concurrent.atomic 패키지에 있는 클래스들은 안전하고 효율적인 기계 수준 명령어를 이용해
    // int, long, boolean, 객체 참조, 배열에 작용하는 연산의 원자성을 보장한다.
    public static AtomicLong count = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 1000; i++) {
            int taskId = i;
            Runnable task = () -> {
                for (int k = 1; k <= 10000; k++)
                    count.incrementAndGet(); // 원자적으로 AtomicLong을 증가시키고 증가한 값 반환
                System.out.println(taskId + ": " + count);
            };
            executor.execute(task);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("Final value: " + count);
    }
}