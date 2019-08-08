package com.example.demo.thread.test;

/**
 * 双重检查创建单例对象
 */
public class Singleton {
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
}
