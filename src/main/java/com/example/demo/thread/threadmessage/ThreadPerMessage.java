package com.example.demo.thread.threadmessage;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


/**
 * @author Jay
 * @description
 * @date Created in 2019/10/30 18:55
 */
public class ThreadPerMessage {

    public static void main(String[] args) throws Exception {


        final ServerSocketChannel ssc =
                ServerSocketChannel.open().bind(
                        new InetSocketAddress(8080));
        //处理请求
        try {
            while (true) {
                // 接收请求
                SocketChannel sc = ssc.accept();
                // 每个请求都创建一个线程
                new Thread(() -> {
                    try {
                        // 读Socket
                        ByteBuffer rb = ByteBuffer
                                .allocateDirect(1024);
                        sc.read(rb);
                        //模拟处理请求
                        Thread.sleep(2000);
                        // 读写转换
                        // 写Socket
                        ByteBuffer wb =
                                (ByteBuffer) rb.flip();
                        sc.write(wb);
                        // 关闭Socket
                        sc.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } finally {
            ssc.close();
        }


    }


    public void test() throws Exception {

        final ServerSocketChannel ssc =
                ServerSocketChannel.open().bind(
                        new InetSocketAddress(8080));
        //处理请求
        try {
            while (true) {
                // 接收请求
                final SocketChannel sc =
                        ssc.accept();

                //                Fiber.schedule(() -> {
                //                    try {
                //                        // 读Socket
                //                        ByteBuffer rb = ByteBuffer
                //                                .allocateDirect(1024);
                //                        sc.read(rb);
                //                        //模拟处理请求
                //                        LockSupport.parkNanos(2000 * 1000000);
                //                        // 写Socket
                //                        ByteBuffer wb =
                //                                (ByteBuffer) rb.flip()
                //                        sc.write(wb);
                //                        // 关闭Socket
                //                        sc.close();
                //                    } catch (Exception e) {
                //                        throw new UncheckedIOException(e);
                //                    }
                //                });
            }//while
        } finally {
            ssc.close();
        }
    }
}
