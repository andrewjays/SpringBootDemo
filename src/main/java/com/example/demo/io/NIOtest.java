package com.example.demo.io;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Jay
 * @description NIO
 * @date Created in 2019/10/28 17:26
 */
public class NIOtest {

    @Test
    public void serverSocketChannelTest() throws Exception {
        // 开启
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 9000));

        // 当 accept()方法返回的时候,它返回一个包含新进来的连接的 SocketChannel。
        // 因此, accept()方法会一直阻塞到有新连接到达
        // 阻塞模式
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 非阻塞模式要判断返回值
        if (socketChannel != null) {

        }
        // 关闭
        serverSocketChannel.close();
    }

    @Test
    public void socketChannelTest() throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        // 连接
        socketChannel.connect(new InetSocketAddress("http://www.baidu.com", 80));

        // 读取数据
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = socketChannel.read(buf);

        // 写入数据
        while (buf.hasRemaining()) {

            socketChannel.write(buf);

        }
        // 全部数据写完,切换到读模式 limit 值标记为buffer中实际数量的大小
        buf.flip();
        // 关闭
        socketChannel.close();

    }

}
