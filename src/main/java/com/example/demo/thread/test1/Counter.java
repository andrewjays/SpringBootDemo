package com.example.demo.thread.test1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public static void main(String[] args) {

        ArrayList<String> s = new ArrayList<>();
        s.add("0");
        ArrayList<String> s1 = new ArrayList<>();
        s1.add("1");
        Set<String> objects = new HashSet<>();
        objects.add("2");
        s.addAll(s1);
        s.addAll(objects);
        System.out.println(s);



    }
}
