package corejava.concurrent;

import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 프로그램에서 시간이 많이 소모되는 작업은 UI 스레드에서 수행하면 안된다. UI가 멈추어 버리기 떄문이다.
 * 여러 스레드에서 UI 요소를 조작하면 UI 요소가 손상될 위험이 있다.
 * JavaFX는 UI 스레드가 아닌 스레드에서 UI에 접근하면 예외를 던진다.
 */
public class WebReader extends Application {
    private TextArea message;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public void start(Stage stage) {
        message = new TextArea("");
        VBox pane = new VBox(10);
        HBox box = new HBox(10);
        Button read = new Button("Read");
        Button quit = new Button("Quit");
        box.getChildren().addAll(read, quit);
        pane.getChildren().addAll(message, box);
        pane.setPadding(new Insets(10));
        stage.setScene(new Scene(pane));
        stage.setTitle("Hello");
        stage.show();

        String url = "https://horstmann.com";

        read.setOnAction(event -> readWithThread(url));
        quit.setOnAction(event -> System.exit(0));
    }

    /**
     * URL에 해당하는 주소의 내용을 읽어온다.
     *
     * @param url 내용을 읽을 URL
     */
    public void read(String url) {
        try {
            try (Scanner in = new Scanner(new URL(url).openStream())) {
                while (in.hasNextLine()) {
                    Platform.runLater(() ->
                            message.appendText(in.nextLine() + "\n"));
                    Thread.sleep(100);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * read를 별도의 스레드로 수행
     *
     * @param url
     */
    public void readWithThread(String url) {
        Runnable task = () -> {
            read(url);
        };
        executor.submit(task);
    }
}