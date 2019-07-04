package com.example.demo.jdk.jdk8;

import org.junit.Test;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
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
                .filter(x -> x.getAge() > 25)
                // 只取5个
                .limit(5)
                // 跳过1个
                .skip(1)
                // 去重，需重写hashcode和equals方法
                .distinct()
                // 终止操作，获取流
                .forEach(System.out::println);

    }

    /**
     * 映射
     * map -- 接受Lambda，将元素转换成其他形式或提取信息，接受一个函数作为参数，
     * 该函数会被应用到每个元素上，并将其映射成一个新的元素
     * <p>
     */
    @Test
    public void test3() {
        list
                .stream()//创建流
                //中间操作：映射
                .map(employee -> employee.getName())
                // 终止流
                .forEach(System.out::println);


    }

    /**
     * flatmap -- 接受一个函数做为参数，将流中的每个值都转换成另一个流，然后将所有流连接成一个流，
     */
    @Test
    public void test4() {

        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        Stream<Stream<Character>> streamStream = list
                .stream()
                // 流中还是流
                .map(StreamTest::getCharacter);
        streamStream
                .forEach(sm -> sm
                        .forEach(System.out::println));
        System.out.println("-------------------------------------");

        list.stream()
                // 大流中直接包含的是流元素，相当于add和addAll的区别。
                .flatMap(StreamTest::getCharacter)
                .forEach(System.out::println);
    }

    private static Stream<Character> getCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    /**
     * 排序
     * <p>
     * sorted -- 自然排序(Comparable)
     * sorted(Comparator com) -- 定制排序(Comparator)
     */
    @Test
    public void test5() {
        List<String> list1 =
                Arrays.asList("ddd", "ccc", "ggg", "bbb", "aaa");

        list1.stream()
                .sorted()// 自然排序
                .forEach(System.out::println);
        System.out.println("------------------------------");

        list.stream()// 定制排序
                .sorted((e1, e2) -> {
                    if (e1.getSalary() == e2.getSalary()) {
                        return e1.getName().compareTo(e2.getName());
                    } else {
                        return e1.getSalary() - e2.getSalary();
                    }
                }).forEach(System.out::println);

    }

    /**
     * Stream的终止操作
     * <p>
     * allMatch – 检查是否匹配所有元素
     * anyMatch – 检查是否至少匹配一个元素
     * noneMatch – 检查是否没有匹配所有元素
     * findFirst – 返回第一个元素
     * count – 返回流中元素的总个数
     * max – 返回流中最大值
     * min – 返回流中最小值
     */
    @Test
    public void test6() {
        boolean b = list.stream()
                .allMatch(emp -> emp.getName().equals("张三"));
        System.out.println(b);

        boolean a = list.stream()
                .anyMatch(emp -> emp.getName().equals("张三"));
        System.out.println(a);

        boolean b2 = list.stream()
                .noneMatch(emp -> emp.getSalary() > 16000);
        System.out.println(b2);

        Optional<Employee> max = list.stream()
                //.max((o1, o2) -> o1.compareTo(o2))
                // 自然排序
                //.max(Comparator.naturalOrder());
                .max(Comparator.comparing(Function.identity()));
        System.out.println(max);

        Optional<Employee> min = list.stream()
                // 自然排序
                //.min(Comparator.naturalOrder());
                //.min((o1, o2) -> o1.compareTo(o2))
                // Function.identity()表示对象本身
                .min(Comparator.comparing(Function.identity()));
        System.out.println(min);
        long count = list.stream()
                .count();
        System.out.println(count);
    }

    /**
     * 终止操作：
     * <p>
     * 归约
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator)
     * -- 可以将流中的元素反复结合起来，得到一个值
     * map和reduce的连接通常称为map-reduce模式，因google用它进行网络搜索而出名
     */
    @Test
    public void test7() {

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        // 全部加起来，再加上1,得到46
        Integer reduce = list1.stream().reduce(1, (x, y) -> x + y);
        System.out.println(reduce);
        Optional<Integer> reduce1 = list.stream()
                // 将salsry映射出来
                .map(Employee::getSalary)
                //.reduce((x, y) -> Integer.sum(x, y));
                // 进行归约求和
                .reduce(Integer::sum);
        System.out.println(reduce1.get());
    }

    /**
     * 收集
     * collect
     * -- 将流转换成其他的形式，接收一个Collector接口的实现，可以通过Collectors的实用类操作
     */
    @Test
    public void test8() {
        // 收集姓名到列表
        List<String> collect = list.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
        System.out.println("-------------------------");

        // 收集姓名到set
        Set<String> collect1 = list.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        collect1.forEach(System.out::println);
        System.out.println("--------------------------");

        // 收集姓名到指定的数据结构
        LinkedHashSet<String> collect2 = list.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        collect2.forEach(System.out::println);
        System.out.println("--------------------------");

        // 总数
        Long collect3 = list.stream()
                // .count()
                .collect(Collectors.counting());
        System.out.println(collect3);
        System.out.println("---------------------------------");
        // 平均
        Double collect4 = list.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(collect4);
        System.out.println("-------------------------------");
        // 总和
        Double collect5 = list.stream()
                // .mapToDouble(Employee::getSalary).sum();
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(collect5);
        System.out.println("-----------------------");

        // 最大值
        Optional<Employee> collect6 = list.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(Employee::getSalary)));
        System.out.println(collect6.get());
        System.out.println("-----------------------");

    }

    /**
     * 分组
     */
    @Test
    public void test9() {
        // 单级分组
        Map<Integer, List<Employee>> collect = list.stream().collect(Collectors.groupingBy(Employee::getAge));
        System.out.println(collect);
        System.out.println("----------------------");
        // 分区--满足条件一个区，不满足另一个区
        Map<Boolean, List<Employee>> collect2 = list.stream().collect(Collectors.partitioningBy(e -> e.getSalary() > 8000));
        System.out.println(collect2);
        System.out.println("-----------------------");
        // 收集各种统计数据
        DoubleSummaryStatistics collect3 = list.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(collect3 + "-----------平均薪水" + collect3.getAverage());

        //连接字符串
        String collect4 = list.stream().map(Employee::getName).collect(Collectors.joining(",", "-----", "-----"));
        System.out.println(collect4);
        System.out.println("-----------------------");
        // 分组排序
        Map<Integer, Map<Integer, List<Employee>>> collect1 = list.stream().
                collect(Collectors.groupingBy(Employee::getAge, Collectors.groupingBy(e -> e.getSalary())));

        System.out.println(collect1);

    }

    /**
     * 练习
     */
    @Test
    public void test10() {
        /**
         * 需求1：
         *给定一个数字，如何返回一个有每个数的平方构成的列表？
         * 给定【1,2,3,4,5】，返回【1,4,9,16,25】
         */
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> collect = list.stream().map(integer -> integer * integer).collect((Collectors.toList()));
        System.out.println(collect);

        /**
         * 需求2：
         * 用reduce和map数一数流中的元素个数
         */
        Optional<Integer> reduce = list.stream()
                // 巧妙之处
                .map(e -> 1)
                .reduce(Integer::sum);
        System.out.println(reduce.get());

    }

}
