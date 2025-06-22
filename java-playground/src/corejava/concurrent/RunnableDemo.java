package corejava.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableDemo {
    public static void main(String[] args) {
        // 다음 방법으로 가용 프로세서 개수를 알아내고, 스레드 개수를 도출할 수 있다.
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("processors = " + processors);

        Runnable hellos = () -> {
            for (int i = 1; i <= 1000; i++)
                System.out.println("Hello " + i);
        };
        Runnable goodbyes = () -> {
            for (int i = 1; i <= 1000; i++)
                System.out.println("Goodbye " + i);
        };

        /*
        ExecutorService 는 태스크를 스케줄링하고 해당 태스크를 수행할 스레드를 선택해 실행한다.
        Executors 클래스에는 스케줄링 정책이 서로 다른 실행자 서비스를 만드는 팩토리 메서드가 있다.
        Executors::newCachedThreadPool - 수명이 짧거나 대부분의 시간을 대기하며 보내는 태스크를 다수 포함하는 프로그램에 최적화된 실행자 서비스를 돌려준다.
        Executors::newFixedThreadPool - 고정 개수 스레드풀을 돌려준다. 태스크를 제출하면 해당 태스크는 스레드를 이용할 수 있을 때까지 큐에 머문다.
        고정 개수 스레드 풀은 계산 집약적인 태스크에 사용하거나 서비스의 리소스 소비를 제한하는 데 적합하다.
         */
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(hellos);
        executor.execute(goodbyes);
        executor.shutdown();
    }
}