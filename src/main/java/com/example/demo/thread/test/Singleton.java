package com.example.demo.thread.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 双重检查创建单例对象
 */
public class Singleton {
    private volatile int c = 90;
    static Singleton instance;

    static Singleton getInstance() {
        
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {

                }
                instance = new Singleton();
            }
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        //        Thread B = new Thread(() -> {
        //            // 主线程调用 B.start() 之前
        //            // 所有对共享变量的修改，此处皆可见
        //            // 此例中，var==77
        //        });
        //        // 此处对共享变量 var 修改
        //        int a = 77;
        //        // 主线程启动子线程
        //        B.start();
        AtomicInteger b = new AtomicInteger(66);
        Thread c = new Thread(() -> {
            // 此处对共享变量 var 修改
            b.set(77);
        });
        // 例如此处对共享变量修改，
        // 则这个修改结果对线程 B 可见
        // 主线程启动子线程
        c.start();
        System.out.println(b.get());
        c.join();
        // 子线程所有对共享变量的修改
        // 在主线程调用 B.join() 之后皆可见
        // 此例中，var==66
        System.out.println(b.get());
    }


}
