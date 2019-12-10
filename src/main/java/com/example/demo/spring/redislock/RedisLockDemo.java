package com.example.demo.spring.redislock;

import org.apache.poi.ss.formula.functions.T;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay
 * @description
 * @date Created in 2019/11/27 17:04
 */
public class RedisLockDemo {

    public static void main(String[] args) throws Exception {

        //        //  https:/github.com/redisson/redisson
        //
        Config config = new Config();

        // 单点
        config.useSingleServer().setAddress("redis://172.18.6.18:6379");

        //        // 哨兵
        //        config.useSentinelServers()
        //                .setMasterName("mymaster")
        //                //可以用"rediss://"来启用SSL连接
        //                .addSentinelAddress("127.0.0.1:26389", "127.0.0.1:26379")
        //                .setDatabase(0);

        final RedissonClient client = Redisson.create(config);

        RLock lock = client.getLock("lock1");

        boolean b = lock.tryLock(10, 1000, TimeUnit.SECONDS);

        long id = Thread.currentThread().getId();
        System.out.println("获得锁线程id:" + id);


        //lock.unlock();


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                long id1 = Thread.currentThread().getId();
                boolean b1 = true;

                try {
                    b1 = lock.tryLock(10, 1000, TimeUnit.SECONDS);
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!b1) {
                    System.out.println("获取失败" + id1);
                }

                System.out.println("线程id:" + id1);
            });
        }
    }

}
