package com.polynomeer.async;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class AsynchronousBlockingClient {
    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 8080);

        // 비동기 연결 요청
        Future<Void> connectFuture = client.connect(hostAddress);
        // 블록킹: 연결 완료까지 대기
        connectFuture.get();

        // 데이터 전송
        String message = "Hello, Server!";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        Future<Integer> writeFuture = client.write(buffer);
        // 블록킹: 쓰기 완료까지 대기
        writeFuture.get();

        // 데이터 읽기
        ByteBuffer readBuffer = ByteBuffer.allocate(256);
        Future<Integer> readFuture = client.read(readBuffer);
        // 블록킹: 읽기 완료까지 대기
        readFuture.get();

        String response = new String(readBuffer.array()).trim();
        System.out.println("Received: " + response);

        client.close();
    }
}