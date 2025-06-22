package corejava.concurrent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CallableDemo {
    /**
     * path 에서 word 가 나타난 횟수를 카운트
     *
     * @param word 카운트할 단어
     * @param path 경로값
     * @return path 에서 word 가 나타난 횟수
     */
    public static long occurrences(String word, Path path) {
        try {
            String contents = Files.readString(path);
            return Pattern.compile("\\PL+")
                    .splitAsStream(contents)
                    .filter(Predicate.isEqual(word))
                    .count();
        } catch (IOException ex) {
            return 0;
        }
    }

    /**
     * 시작 경로를 받아서 하위 경로 엔트리 Set 리턴
     *
     * @param p 시작 경로를 받아서
     * @return 하위 경로 모든 엔트리에 대한 Set 리턴
     * @throws IOException Files 객체에 대한 파일입출력 예외
     */
    public static Set<Path> descendants(Path p) throws IOException {
        try (Stream<Path> entries = Files.walk(p)) {
            return entries.collect(Collectors.toSet());
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        String word = "String";
        Set<Path> paths = descendants(Paths.get("."));
        List<Callable<Long>> tasks = new ArrayList<>();
        for (Path p : paths) {
            tasks.add(() -> occurrences(word, p));
        }
        int processors = Runtime.getRuntime().availableProcessors(); // 가용 프로세서 개수를 얻어서
        ExecutorService executor = Executors.newFixedThreadPool(processors); // 프로세서 개수만큼 스레드풀을 생성
        List<Future<Long>> results = executor.invokeAll(tasks); // 모든 테스크가 완료될 때까지 블록
        long total = 0;
        for (Future<Long> result : results) total += result.get();
        System.out.println("Occurrences of String: " + total);

        String searchWord = "occurrences";
        List<Callable<Path>> searchTasks = new ArrayList<>();
        for (Path p : paths)
            searchTasks.add(
                    () -> {
                        if (occurrences(searchWord, p) > 0) return p;
                        else throw new RuntimeException();
                    });
        Path found = executor.invokeAny(searchTasks); // 제출한 태스크 중 하나가 정상적으로 완료되면 즉시 반환
        System.out.println(found);
        executor.shutdown();
    }
}