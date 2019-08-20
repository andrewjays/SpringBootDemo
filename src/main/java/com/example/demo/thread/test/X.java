package com.example.demo.thread.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class X {
    private final Lock rtl =
            new ReentrantLock();
    int value;

    public int get() {
        // 获取锁
        rtl.lock();        // ②
        try {
            return value;
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }

    public void addOne() {
        // 获取锁
        rtl.lock();
        try {
            value = 1 + get(); // ① 调用get()方法,锁还没释放,可重入锁可以再次进入
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }
}
