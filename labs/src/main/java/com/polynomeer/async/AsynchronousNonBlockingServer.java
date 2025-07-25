package com.polynomeer.async;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AsynchronousNonBlockingServer {
    public static void main(String[] args) throws IOException {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
        server.bind(new InetSocketAddress(8080));
        System.out.println("Server started on port 8080");

        // 비동기 클라이언트 연결 대기
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Void attachment) {
                // 다음 연결 대기
                server.accept(null, this);
                System.out.println("Client connected");

                // 데이터 읽기
                ByteBuffer buffer = ByteBuffer.allocate(256);
                client.read(buffer, null, new CompletionHandler<Integer, Void>() {
                    @Override
                    public void completed(Integer result, Void attachment) {
                        String message = new String(buffer.array()).trim();
                        System.out.println("Received: " + message);

                        // 에코 응답
                        buffer.clear();
                        buffer.put(("Echo: " + message).getBytes());
                        buffer.flip();
                        client.write(buffer);
                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {
                        System.err.println("Read failed: " + exc);
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.err.println("Accept failed: " + exc);
            }
        });

        // 서버가 종료되지 않도록 대기
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}