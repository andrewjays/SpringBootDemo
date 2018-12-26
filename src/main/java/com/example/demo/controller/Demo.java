package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component                //实例化
@Configurable             //注入bean
@EnableScheduling
public class Demo {
    private final static Logger logger = LoggerFactory.getLogger(Demo.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(cron = " 0/5 * * * * ? ")
    public void timePlan() {
        logger.info("这是一个测试用例");
        logger.info("现在时间:"+dateFormat.format(new Date()));
    }
}
