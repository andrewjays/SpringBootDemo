package com.example.demo.spring.springtask;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Jay
 * @description
 * @date Created in 2019/11/26 17:10
 */
//@Service
//@EnableScheduling
public class TaskServiceImpl implements SchedulingConfigurer {

    public void cacheTask(ScheduledTaskRegistrar taskRegistrar, int i) {

        Runnable task = () -> {
            long l = System.currentTimeMillis();
            //任务逻辑代码部分.
            try {
                Thread.sleep(i * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long l1 = System.currentTimeMillis();
            long l2 = l1 - l;
            System.out.println("线程名称:-------" + Thread.currentThread().getName() + "\n任务号:----------" + i
                    + "\n任务号:----------" + i + "\n执行时间:----------" + l2 + "\n\n" + "\n当前时间:----------" + new Date().toString());


        };
        Trigger trigger = triggerContext -> {
            //任务触发，可修改任务的执行周期.
            //每一次任务触发，都会执行这里的方法一次，重新获取下一次的执行时间
            CronTrigger trigger1 = new CronTrigger("0/" + i * 5 + " * * * * ?");

            Date nextExec = trigger1.nextExecutionTime(triggerContext);
            return nextExec;
        };
        taskRegistrar.addTriggerTask(task, trigger);

    }

    /**
     * 定时任务
     *
     * @param taskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        System.out.println("----------------------方法执行了");
        for (int i = 1; i <= 3; i++) {
            cacheTask(taskRegistrar, i);

        }

    }


}
