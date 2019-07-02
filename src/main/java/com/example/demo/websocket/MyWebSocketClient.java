package com.example.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


/**
 * @author Jay
 * @description 客户端
 * @date Created in 2019/7/2 11:53
 */
@Slf4j
public class MyWebSocketClient extends WebSocketClient {
    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("已连接");
    }

    @Override
    public void onMessage(String s) {
        log.info("收到消息:"+s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("已断开");
    }

    @Override
    public void onError(Exception e) {
        log.info("出错");
    }
}
