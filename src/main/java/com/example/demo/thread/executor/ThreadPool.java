package com.example.demo.thread.executor;

/**
 * @author Jay
 * @description 线程池:一般池化资源的设计方法
 * @date Created in 2019/10/17 15:22
 */
class ThreadPool {

    /**
     * 获取空闲线程
     */
    Thread acquire() {
        return new Thread();
    }

    /**
     * 释放线程
     */
    void release(Thread t) {

    }

    // 期望的使用
    ThreadPool pool;
    Thread T1 = pool.acquire();
    // 传入runnable对象
    //T1.execute(()->

    { // 具体业务逻辑
    }
}
