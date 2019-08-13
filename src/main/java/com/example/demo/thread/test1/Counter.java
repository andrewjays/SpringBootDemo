package com.example.demo.thread.test1;

/**
 * 面向对象思想与并发编程
 * <p>
 * 封装
 */
public class Counter {
    private long value;

    /**
     * 制定并发访问策略
     *
     * @return
     */
    synchronized long get() {
        return value;
    }

    synchronized long addOne() {
        return ++value;
    }
}
