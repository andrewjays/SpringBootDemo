package com.example.demo.thread.test1;

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
}
