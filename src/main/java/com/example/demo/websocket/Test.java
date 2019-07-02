package com.example.demo.websocket;


import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Jay
 * @description
 * @date Created in 2019/7/2 16:31
 */
@Slf4j
public class Test {


    public static void main(String[] args) throws Exception {
        MyWebSocketClient myClient =
                new MyWebSocketClient(new URI("ws://127.0.0.1:9000/webSocket"));
        // 往websocket服务端发送数据
        myClient.connect();
        Thread.sleep(3000);
        log.info("已连接成功");
        myClient.send("测试连接");

    }
}
