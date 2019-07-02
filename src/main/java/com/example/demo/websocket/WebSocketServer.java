package com.example.demo.websocket;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Jay
 * @description 服务端
 * @date Created in 2019/7/2 14:10
 */

@ServerEndpoint(value = "/webSocket")
@Slf4j
public class WebSocketServer {
    // 线程安全的静态变量，表示在线连接数
    private static volatile int onlineCount = 0;
    // 用来存放每个客户端对应的WebSocketTest对象，适用于同时与多个客户端通信,用于读大于写的操作
    public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    //若要实现服务端与指定客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static ConcurrentHashMap<Session, Object> webSocketMap = new ConcurrentHashMap<Session, Object>();



}
