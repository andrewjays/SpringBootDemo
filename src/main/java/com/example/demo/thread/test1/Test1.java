package com.example.demo.thread.test1;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author Jay
 * @description
 * @date Created in 2020/1/9 15:02
 */
public class Test1 {

    public static void main(String[] args) {
//        // 获取 Java 线程管理 MXBean
//        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
//        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
//        // 遍历线程信息，仅打印线程 ID 和线程名称信息
//        for (ThreadInfo threadInfo : threadInfos) {
//            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
//        }

        File file = new File("D:\\1.txt");
        if(file.length()==0){
            System.out.println("1111111");
        }

    }
}
