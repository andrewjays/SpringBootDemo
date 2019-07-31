package com.example.demo.jdk.jdktool;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Jay
 * @description
 * @date Created in 2019/6/25 9:48
 */
public class Test {
    private int size;
    private String name;
    private String brother;
    private long create_time;

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(Test.class).toPrintable());
    }
    
}
