package com.example.demo.tool.websocket.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Jay
 * @description
 * @date Created in 2019/7/2 15:47
 */
@Configuration
@ConditionalOnWebApplication
public class WebSocketConfig {

    /**
     * 使用boot内置tomcat时需要注入此bean
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public MySpringConfigurator mySpringConfigurator() {
        return new MySpringConfigurator();
    }
}
