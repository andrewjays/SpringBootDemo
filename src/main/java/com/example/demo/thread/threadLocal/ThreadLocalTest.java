package com.example.demo.thread.threadLocal;

/**
 * @author Jay
 * @description 线程变量
 * @date Created in 2019/10/29 19:00
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();
        local.set("test");
        String s = local.get();
        System.out.println(s);
    }
}
