package com.example.demo.thread.netty;

import org.python.netty.bootstrap.ServerBootstrap;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelInitializer;
import org.python.netty.channel.EventLoopGroup;
import org.python.netty.channel.nio.NioEventLoopGroup;
import org.python.netty.channel.socket.SocketChannel;
import org.python.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @author Jay
 * @description netty 线程管理
 * @date Created in 2019/11/9 12:48
 */
public class NettyTest {

    public static void main(String[] args) {

        //        默认“2*CPU 核数”个线程(netty源码)
        //        private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("org.python.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
        //
        //    protected MultithreadEventLoopGroup( int nThreads, ThreadFactory threadFactory, Object...args){
        //            super(nThreads == 0 ? DEFAULT_EVENT_LOOP_THREADS : nThreads, threadFactory, args);
        //        }

        // 事件处理器
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // boss线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        // worker线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>
                    () {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {

                    socketChannel.pipeline().addLast(serverHandler);

                }
            });
            //  bind服务端端口
            ChannelFuture f = b.bind(9090).sync();

            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            // 终止工作线程组
            workerGroup.shutdownGracefully();
            // 终止boss线程组
            bossGroup.shutdownGracefully();
        }

    }
}
