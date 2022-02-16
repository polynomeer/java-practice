package corejava.concurrent;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture 는 프로그래머가 수동으로 완료값(completion value)을 설정할 수 있어 완료 가능하다고 말한다.
 * (JavaScript Promise 객체와 유사)
 */
public class AsyncDemo {
    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        String urlString = "https://horstmann.com";
        ExecutorService executor = Executors.newCachedThreadPool();

        HttpClient client = HttpClient.newBuilder()
                .executor(executor)
                .build();
        HttpRequest request = HttpRequest.newBuilder(new URI(urlString)).GET().build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .whenComplete((s, t) -> { // 결과를 내며 완료 or 미처리 예외를 던지며 완료
                    if (t == null) System.out.println(s); // 결과 s를 처리한다.
                    else t.printStackTrace(); // Throwable t를 처리한다.
                    executor.shutdown();
                });
        executor.awaitTermination(10, TimeUnit.MINUTES);
    }
}