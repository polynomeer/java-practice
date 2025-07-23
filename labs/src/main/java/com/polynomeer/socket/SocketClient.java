package com.polynomeer.socket;

import java.io.*;
import java.net.*;

public class SocketClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999); // 서버 접속
            System.out.println("서버에 연결됨");

            // 서버로 데이터 보내기 위한 출력 스트림
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // 서버로부터 응답 받기 위한 입력 스트림
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 사용자 입력 받기
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String userLine;
            while ((userLine = userInput.readLine()) != null) {
                out.println(userLine); // 서버에 전송
                String response = in.readLine(); // 서버로부터 응답
                System.out.println("서버 응답: " + response);
                if (userLine.equalsIgnoreCase("exit")) break;
            }

            out.close();
            in.close();
            socket.close();
            System.out.println("클라이언트 종료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
