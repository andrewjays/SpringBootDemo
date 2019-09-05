package com.example.demo.thread.aomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 原子类
 */
public class Test {
    // long count =0;
    AtomicLong count =
            new AtomicLong(0);
    void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            //count+=1;
            count.getAndIncrement();
        }
    }
}
