package com.example.demo.thread.completionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Jay
 * @description
 * @date Created in 2019/10/22 16:32
 */
public class Test1 {
    public static void main(String[] args) {

        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 异步向电商S1询价
        //        Future<Integer> f1 =
        //                executor.submit(
        //                        ()->getPriceByS1());
        //        // 异步向电商S2询价
        //        Future<Integer> f2 =
        //                executor.submit(
        //                        ()->getPriceByS2());
        //        // 异步向电商S3询价
        //        Future<Integer> f3 =
        //                executor.submit(
        //                        ()->getPriceByS3());
        //
        //        // 获取电商S1报价并保存
        //        r=f1.get();
        //        executor.execute(()->save(r));
        //
        //        // 获取电商S2报价并保存
        //        r=f2.get();
        //        executor.execute(()->save(r));
        //
        //        // 获取电商S3报价并保存
        //        r=f3.get();
        //        executor.execute(()->save(r));


        //        // 创建阻塞队列
        //        BlockingQueue<Integer> bq =
        //                new LinkedBlockingQueue<>();
        //        //电商S1报价异步进入阻塞队列
        //        executor.execute(()->
        //                bq.put(f1.get()));
        //        //电商S2报价异步进入阻塞队列
        //        executor.execute(()->
        //                bq.put(f2.get()));
        //        //电商S3报价异步进入阻塞队列
        //        executor.execute(()->
        //                bq.put(f3.get()));
        //        //异步保存所有报价
        //        for (int i=0; i<3; i++) {
        //            Integer r = bq.take();
        //            executor.execute(()->save(r));
        //        }


        //        // 创建线程池
        //        ExecutorService executor =
        //                Executors.newFixedThreadPool(3);
        //        // 创建CompletionService
        //        CompletionService<Integer> cs = new
        //                ExecutorCompletionService<>(executor);
        //        // 异步向电商S1询价
        //        cs.submit(()->getPriceByS1());
        //        // 异步向电商S2询价
        //        cs.submit(()->getPriceByS2());
        //        // 异步向电商S3询价
        //        cs.submit(()->getPriceByS3());
        //        // 将询价结果异步保存到数据库
        //        for (int i=0; i<3; i++) {
        //            Integer r = cs.take().get();
        //            executor.execute(()->save(r));
        //        }


        //        // 创建线程池
        //        ExecutorService executor =
        //                Executors.newFixedThreadPool(3);
        //        // 创建CompletionService
        //        CompletionService<Integer> cs =
        //                new ExecutorCompletionService<>(executor);
        //        // 用于保存Future对象
        //        List<Future<Integer>> futures =
        //                new ArrayList<>(3);
        //        //提交异步任务，并保存future到futures
        //        futures.add(
        //                cs.submit(()->geocoderByS1()));
        //        futures.add(
        //                cs.submit(()->geocoderByS2()));
        //        futures.add(
        //                cs.submit(()->geocoderByS3()));
        //        // 获取最快返回的任务执行结果
        //        Integer r = 0;
        //        try {
        //            // 只要有一个成功返回，则break
        //            for (int i = 0; i < 3; ++i) {
        //                r = cs.take().get();
        //                //简单地通过判空来检查是否成功返回
        //                if (r != null) {
        //                    break;
        //                }
        //            }
        //        } finally {
        //            //取消所有任务
        //            for(Future<Integer> f : futures)
        //                f.cancel(true);
        //        }
        //        // 返回结果
        //        return r;
    }
}
