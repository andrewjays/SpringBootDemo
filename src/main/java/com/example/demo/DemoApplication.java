package com.example.demo;


import com.example.demo.springcontext.RContextTest;
import com.example.demo.springcontext.ContextTest1;
import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


//@EnableZipkinServer
@EnableWebSocket
@EnableEurekaServer
@ServletComponentScan
@SpringBootApplication
@EnableSwagger2
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
        // 默认注入名称为类名小写
        RContextTest RContextTest = run.getBean("RContextTest", RContextTest.class);

        ContextTest1 contextTest1 = run.getBean("contextTest1Impl", ContextTest1.class);
        String[] s = run.getBeanDefinitionNames();
        contextTest1.test();

        System.out.println(s.toString());
    }


}
