package com.example.demo.thread.lock;

class SampleLock {
    volatile int state;

    // 加锁
    void lock() {
        // 省略代码无数
        state = 1;
    }

    // 解锁
    void unlock() {
        // 省略代码无数
        state = 0;
    }
}
