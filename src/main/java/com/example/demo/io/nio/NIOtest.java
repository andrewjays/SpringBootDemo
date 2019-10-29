package com.example.demo.io.nio;

import com.ziclix.python.sql.connect.Connect;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

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

    /**
     * buffer 常用方法
     *
     * @throws Exception
     */
    @Test
    public void bufferTest() throws Exception {
        // 获取buffer对象
        ByteBuffer buf = ByteBuffer.allocate(48);

        // 向buffer写入数据
        buf.put("测试写入".getBytes());

        // 从buffer读数据
        byte b = buf.get();

        // 如果要读取 Buffer 中的数据，需要切换模式，从写模式切换到读模式.
        buf.flip();

        // 重置 position 的值为 0 因此，我们可以重新读取和写入 Buffer 了
        // 大多数情况下，该方法主要针对于读模式，所以可以翻译为“倒带”。
        buf.rewind();

        // 清空数据
        buf.clear();

        // 保存当前的 position 到 mark 中
        buf.mark();

        //  恢复当前的 postion 为 mark
        buf.reset();

    }

    /**
     * selector 常用方法,多路复用
     * <p>
     * 用于轮询一个或多个 NIO Channel 的状态是否处于可读、可写。
     */
    @Test
    public void selectorTest() {

        try {
            // 创建选择器
            Selector selector = Selector.open();

            ServerSocketChannel channel = ServerSocketChannel.open();

            // 一个Channel要被注册,必须是非阻塞的
            channel.configureBlocking(false);

            // 将Channel 要注册到 Selector
            // Connect ：连接完成事件( TCP 连接 )，仅适用于客户端，对应 SelectionKey.OP_CONNECT 。
            // Accept ：接受新连接事件，仅适用于服务端，对应 SelectionKey.OP_ACCEPT 。
            // Read ：读事件，适用于两端，对应 SelectionKey.OP_READ ，表示 Buffer 可读。
            // Write ：写时间，适用于两端，对应 SelectionKey.OP_WRITE ，表示 Buffer 可写。
            SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ);

            // 或运算来组合多个事件
            // 事件设计采用位计算,即每次或结果都唯一,则可以确定唯一组合
            int interestSet1 = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

            // 可以修改订阅结果
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            // 返回感兴趣的集合
            int interestSet = selectionKey.interestOps();

            // boolean isInterestedInAccept  = interestSet & SelectionKey.OP_ACCEPT != 0;
            // boolean isInterestedInConnect = interestSet & SelectionKey.OP_CONNECT != 0;
            // boolean isInterestedInRead    = interestSet & SelectionKey.OP_READ != 0;
            // boolean isInterestedInWrite   = interestSet & SelectionKey.OP_WRITE != 0;

            // 判断哪些事件已就绪
            int readySet = selectionKey.readyOps();

            selectionKey.isAcceptable();
            selectionKey.isConnectable();
            selectionKey.isReadable();
            selectionKey.isWritable();

            // 添加附加对象
            selectionKey.attach(new Object());
            Object attachedObj = selectionKey.attachment();

            // 在注册时添加对象
            SelectionKey key = channel.register(selector, SelectionKey.OP_READ, new Object());

            // select 方法返回的 int 值，表示有多少 Channel 已经就绪。
            // 亦即，自上次调用 select 方法后有多少 Channel 变成就绪状态。
            // 如果调用 select 方法，因为有一个 Channel 变成就绪状态则返回了 1 ；
            // 若再次调用 select 方法，如果另一个 Channel 就绪了，它会再次返回1。
            // 如果对第一个就绪的 Channel 没有做任何操作，现在就有两个就绪的 Channel ，
            // 但在每次 select 方法调用之间，只有一个 Channel 就绪了，所以才返回 1
            int select = selector.select();

            // 当有新增就绪的 Channel ，需要先调用 select 方法，才会添加到“已选择键集(selected key set)”中。
            // 否则，我们直接调用 #selectedKeys() 方法，是无法获得它们对应的 SelectionKey 们。
            Set selectedKeys = selector.selectedKeys();

            // 唤醒
            selector.wakeup();

            // 关闭
            selector.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * selector 使用实例
     */
    @Test
    public void selectorTest1() throws IOException {
        // 创建channel
        ServerSocketChannel channel = ServerSocketChannel.open();

        // 创建selector
        Selector selector = Selector.open();

        // 设置channel 为非阻塞模式
        channel.configureBlocking(false);

        // 注册channel到selector
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);

        while (true) {

            // 通过 Selector 选择 Channel
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }

            // 获得可操作的 Channel
            Set selectedKeys = selector.selectedKeys();

            // 遍历 SelectionKey 数组
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key1 = keyIterator.next();
                if (key1.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key1.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key1.isReadable()) {
                    // a channel is ready for reading
                } else if (key1.isWritable()) {
                    // a channel is ready for writing
                }
                // 移除
                keyIterator.remove(); // <1>
            }
        }
    }


    public static void main(String[] args) {

        int a = 1 << 0;
        int b = 1 << 2;
        int c = 1 << 3;
        int d = 1 << 4;

        System.out.println(a | b);
        System.out.println(b | c);
        System.out.println(c | d);
        System.out.println(d | a);

    }
}
