package com.polynomeer.socket;

import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999); // 포트 9999에서 대기
            System.out.println("서버 시작, 클라이언트 연결 대기 중...");

            Socket clientSocket = serverSocket.accept(); // 클라이언트 연결 수락
            System.out.println("클라이언트 연결됨: " + clientSocket.getInetAddress());

            // 입력 스트림 (클라이언트 → 서버)
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // 출력 스트림 (서버 → 클라이언트)
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("클라이언트로부터 받은 메시지: " + inputLine);
                out.println("서버 응답: " + inputLine); // 에코(echo) 응답
                if (inputLine.equalsIgnoreCase("exit")) break;
            }

            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("서버 종료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
