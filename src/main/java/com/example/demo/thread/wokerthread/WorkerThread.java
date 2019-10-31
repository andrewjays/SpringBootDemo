package com.example.demo.thread.wokerthread;

import org.hibernate.validator.constraints.SafeHtml;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

/**
 * @author Jay
 * @description
 * @date Created in 2019/10/31 10:14
 */
public class WorkerThread {
    public static void main(String[] args) throws Exception {

        ExecutorService es = Executors
                .newFixedThreadPool(500);
        final ServerSocketChannel ssc =
                ServerSocketChannel.open().bind(
                        new InetSocketAddress(8080));

        // 手动创建线程池,使用有界队列,避免OOMw
        ExecutorService es1 = new ThreadPoolExecutor(
                50, 500,
                60L, TimeUnit.SECONDS,
                //注意要创建有界队列
                new LinkedBlockingQueue<Runnable>(2000),
                //建议根据业务需求实现ThreadFactory
                r -> {
                    return new Thread(r, "echo-" + r.hashCode());
                },
                //建议根据业务需求实现RejectedExecutionHandler
                new ThreadPoolExecutor.CallerRunsPolicy());


        //处理请求
        try {
            while (true) {
                // 接收请求
                SocketChannel sc = ssc.accept();
                // 将请求处理任务提交给线程池
                es.execute(() -> {
                    try {
                        // 读Socket
                        ByteBuffer rb = ByteBuffer
                                .allocateDirect(1024);
                        sc.read(rb);
                        //模拟处理请求
                        Thread.sleep(2000);
                        // 写Socket
                        ByteBuffer wb =
                                (ByteBuffer) rb.flip();
                        sc.write(wb);
                        // 关闭Socket
                        sc.close();
                    } catch (Exception e) {
                    }
                });
            }
        } finally {
            ssc.close();
            es.shutdown();
        }
    }

    @Test
    public void test() throws InterruptedException {

        // L1、L2阶段共用的线程池
        // 若线程池大小为2,则L1阶段俩个线程全部启用,此时线程里所有线程都被阻塞
        // L2阶段无法执行
        ExecutorService es = Executors.
                newFixedThreadPool(4);
        // L1阶段的闭锁
        CountDownLatch l1 = new CountDownLatch(2);
        for (int i = 0; i < 2; i++) {
            System.out.println("L1");
            // 执行L1阶段任务
            es.execute(() -> {
                // L2阶段的闭锁
                CountDownLatch l2 = new CountDownLatch(2);
                // 执行L2阶段子任务
                for (int j = 0; j < 2; j++) {
                    es.execute(() -> {
                        System.out.println("L2");
                        l2.countDown();
                    });
                }
                // 等待L2阶段任务执行完
                try {
                    l2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                l1.countDown();
            });
        }
        // 等着L1阶段任务执行完
        l1.await();
        System.out.println("end");
    }
}
