package com.example.demo.spring.springtask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author Jay
 * @description
 * @date Created in 2019/11/13 16:47
 */
@Configuration
public class ScheduleConfig {
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("accop-scheduler-");
        return scheduler;
    }
}


