package com.example.demo.jdk8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Jay
 * @description 一系列流水线式的中间操作。
 * <p>
 * 流是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。
 * <p>
 * 注意：
 * ①Stream自己不会存储元素。
 * ②Stream不会改变源对象。相反，会返回持有新结果的新Stream。
 * ③Stream操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。
 * <p>
 * 操作的三个步骤：
 * 1、创建一个流Stream
 * 2、中间操作
 * 3、终止操作
 * @date Created in 2019/7/2 19:17
 */
public class StreamTest {
    /**
     * 流的创建
     */
    @Test
    public void test1() {
        // 1.可以通过Collections系列集合提供的stream()或parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();

        // 2. 通过Arrays中的静态方法stream()获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(emps);

        // 3.通过Stream类中的静态方法of()
        Stream<String> stream3 = Stream.of("", "", "");

        // 4.创建无限流
        // 迭代
        
    }
}
