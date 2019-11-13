package com.example.demo.thread.netty;

import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Jay
 * @description socket连接处理器
 * @date Created in 2019/11/9 12:50
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 处理读事件
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
    }

    /**
     * 处理读完成事件
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * 处理异常事件
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
