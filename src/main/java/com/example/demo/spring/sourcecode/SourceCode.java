package com.example.demo.spring.sourcecode;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Jay
 * @description 源码学习
 * @date Created in 2019/12/18 15:57
 */
public class SourceCode {

    public static void main(String[] args) {
        // 从xml文件读取
        ApplicationContext context = new ClassPathXmlApplicationContext("");

        // 从文件系统中读取
        ApplicationContext context1 = new FileSystemXmlApplicationContext("");

        
    }
}
