package com.example.demo.jdk8;

import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
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
        Stream.iterate(0, x -> x + 2).limit(10).forEach(System.out::println);
        // 创建
        Stream<Double> generate = Stream.generate(() -> Math.random());
        generate.limit(5).forEach(System.out::println);
    }

    List<Employee> list = Arrays.asList(
            new Employee("张三", "上海", 5000, 22),
            new Employee("李四", "北京", 4000, 23),
            new Employee("c五", "日本", 6000, 50),
            new Employee("b七", "香港", 7000, 50),
            new Employee("赵六", "纽约", 1000, 8)
    );

    /**
     * 多个中间操作可以连接起来形成一个流水线，除非流水想上出发终止操作。
     * 否则中间操作不会执行任何的处理，而在终止操作时一次性全部处理，成为“惰性求值”。
     * <p>
     * 筛选和切片
     * filter -- 接受Lambda，从流中排除某些元素
     * limit -- 截断流，使其元素不超过某个给定数量
     * skip -- 跳过元素，返回一个扔掉了前n个元素的流，若流中元素不足n个，则返回一个空流，与limit互补。
     * distinct -- 去重，通过hashcode和equals去重。
     */
    @Test
    public void test2() {
        // 创建流
        Stream<Employee> stream = list.stream();

        stream
                // 过滤符合条件的流元素
                .filter(x->x.getAge()>25)
                //

    }


}
