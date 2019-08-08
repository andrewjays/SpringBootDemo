package com.example.demo.thread.test;

class VolatileExample {
    int x = 0;
    volatile boolean v = false;

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        if (v == true) {
            // 直觉上看，应该是 42，
            // 那实际应该是多少呢？这个要看 Java 的版本，
            // 如果在低于 1.5 版本上运行，x 可能是 42，也有可能是 0；
            // 如果在 1.5 以上的版本上运行，x 就是等于 42。
            System.out.println(x);
        }
    }
}
