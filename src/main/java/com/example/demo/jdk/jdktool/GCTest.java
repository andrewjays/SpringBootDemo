package com.example.demo.jdk.jdktool;

/**
 * @author Jay
 * @description gc分析
 * 打印GC日志
 * -XX:+PrintGC
 * 虚拟机关闭指针逃逸分析
 * -XX:-DoEscapeAnalysis
 * 虚拟机关闭标量替换
 * -XX:-EliminateAllocations
 * <p>
 * 若没有指针指向新建对象则对象在栈中,有指针指向该对象
 * 则开始移到堆中eden区
 * @date Created in 2019/6/25 9:59
 */
public class GCTest {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            new Test();

        }
        System.out.println("take time:" + (System.currentTimeMillis() - startTime) + "ms");
    }

}

