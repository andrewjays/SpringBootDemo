package com.example.demo.thread.test1;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class Test {
    private long count = 0;

    synchronized long get() {
        return count;
    }

    synchronized void set(long v) {
        count = v;
    }

    void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            set(get() + 1);
        }
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        });
        singleThreadPool.shutdown();
        System.out.println(Thread.currentThread().getName());

    }

}
