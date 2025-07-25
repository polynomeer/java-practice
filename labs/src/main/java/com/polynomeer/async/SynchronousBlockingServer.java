package com.polynomeer.async;

import java.io.*;
import java.net.*;

public class SynchronousBlockingServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started on port 8080");

        while (true) {
            // 클라이언트 연결 대기 (블록킹)
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            // 입출력 스트림 생성 (블록킹)
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // 클라이언트 메시지 읽기 (블록킹)
            String message = in.readLine();
            System.out.println("Received: " + message);

            // 에코 응답
            out.println("Echo: " + message);

            // 연결 종료
            clientSocket.close();
        }
    }
}