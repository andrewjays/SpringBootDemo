package com.example.demo.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay
 * @description 异步通知实例
 * @date Created in 2019/10/19 14:03
 */
public class FutureTaskTest1 {
    public static void main(String[] args) throws Exception {

        // 创建任务 T2 的 FutureTask
        FutureTask<String> ft2
                = new FutureTask<>(new T2Task());
        // 创建任务 T1 的 FutureTask
        FutureTask<String> ft1
                = new FutureTask<>(new T1Task(ft2));
        // 线程 T1 执行任务 ft1
        Thread T1 = new Thread(ft1);
        T1.start();
        // 线程 T2 执行任务 ft2
        Thread T2 = new Thread(ft2);
        T2.start();
        // 等待线程 T1 执行结果
        System.out.println(ft1.get());
    }
}

class T1Task implements Callable<String> {

    FutureTask<String> ft2;

    /**
     * T1任务需要T2任务的futureTask
     *
     * @param ft2
     * @return
     */
    T1Task(FutureTask<String> ft2) {
        this.ft2 = ft2;
    }


    @Override
    public String call() throws Exception {
        System.out.println("T1:洗水壶...");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T1:烧开水...");
        TimeUnit.SECONDS.sleep(15);

        // 获取T2线程的茶叶
        String s = ft2.get();
        System.out.println("T1:拿到茶叶:" + s);

        System.out.println("T1:泡茶...");

        return "上茶:" + s;

    }
}

/**
 * T2Task 需要执行的任务:
 */
class T2Task implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("T2: 洗茶壶...");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T2: 洗茶杯...");
        TimeUnit.SECONDS.sleep(2);

        System.out.println("T2: 拿茶叶...");
        TimeUnit.SECONDS.sleep(1);
        return " 龙井 ";
    }
}


