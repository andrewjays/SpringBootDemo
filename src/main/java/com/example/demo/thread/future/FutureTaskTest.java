package com.example.demo.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author Jay
 * @description
 * @date Created in 2019/10/19 13:35
 */
public class FutureTaskTest {

    public static void main(String[] args) throws Exception {
        // 创建FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1 + 2);
        //        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
        //            @Override
        //            public Integer call() throws Exception {
        //                return 1 + 2;
        //            }
        //        });
        // 创建线程池
        ExecutorService es = Executors.newCachedThreadPool();
        // 提交FutureTask
        es.submit(futureTask);
        // 获取计算结果
        Integer result = futureTask.get();
        System.out.println(result);

        // 线程应用
        Thread t1 = new Thread(futureTask);
        t1.start();
        // 获取计算结果
        Integer integer = futureTask.get();
    }
}
