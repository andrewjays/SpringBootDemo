package com.example.demo.jdk.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Jay
 * @description jdk 1.8 新特性
 * <p>
 * 1. 速度更快 – 红黑树
 * 2. 代码更少 – Lambda
 * 3. 强大的 –Stream API – Stream
 * 4. 便于并行 – Parallel
 * 5. 最大化减少空指针异常 – Optional
 * @date Created in 2019/7/2 17:22
 */
public class LambdaTest {

    /**
     * 基本lambda表达式
     * 左右遇一括号省，左侧推断类型省,能省则省。
     */
    private void lambdaTest() {
        // 1.无参数,无返回值
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        Runnable r2 = () -> System.out.println("hello lambda");
        // 2.有一个参数，并且无返回值
        List<Integer> list = Arrays.asList(1, 2, 3);
        for (Integer x : list) {
            System.out.println(x);
        }
        list.forEach((x) -> System.out.println(x));
        // 3.若只有一个参数,小括号可以不写
        list.forEach(x -> System.out.println(x));
        // 4. 有两个以上的参数，有返回值，并且Lambda体中有多条语句
        Comparator<Integer> c2 = new Comparator<Integer>() {
            @Override
            public int compare(Integer x, Integer y) {
                System.out.print(Integer.compare(x, y) + "函数式接口");
                return Integer.compare(x, y);
            }
        };
        Comparator<Integer> c1 = (x, y) -> {
            System.out.print(Integer.compare(x, y) + "函数式接口");
            return Integer.compare(x, y);
        };
        // 5.若Lambda体中只有一条语句，return和大括号都可以省略不写
        Comparator<Integer> c4 = new Comparator<Integer>() {
            @Override
            public int compare(Integer x, Integer y) {
                return Integer.compare(x, y);
            }
        };
        Comparator<Integer> c5 = (Integer x, Integer y) -> Integer.compare(x, y);
        // 6.Lambda表达式的参数列表的数据类型可以省略不写，
        // 因为JVM编译器可以通过上下文进行类型推断出数据类型，既“类型推断”
        Comparator<Integer> c6 = (x, y) -> Integer.compare(x, y);
        /**
         * 1.方法引用
         * 对象：：实例方法名
         *
         * 类：：静态方法名
         *
         * 类：：实例方法名
         *
         * 注意：
         * [1].Lambda体中调用方法的参数列表与返回值类型，
         * 要与函数式接口中抽象方法的函数列表和返回值类型一致。
         * [2].若Lambda参数列表中的第一参数是实例方法的调用者，
         * 而第二个参数是实例方法的参数时，可以使用ClassName：：method进行调用。
         *
         * 2.构造器引用
         * 格式：
         *
         * ClassName：：new
         *
         * 注意：需要引用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致。
         *
         * 3.数组引用
         * Type[]：：new
         */
        Comparator<Integer> c7 = Integer::compare;
    }

    // Lambda表达式的函数式接口
    List<Employee> list = Arrays.asList(
            new Employee("张三", "上海", 5000, 22),
            new Employee("李四", "北京", 4000, 23),
            new Employee("c五", "日本", 6000, 50),
            new Employee("b七", "香港", 7000, 50),
            new Employee("赵六", "纽约", 1000, 8)
    );

    /**
     * 需求1：lambda表达式的使用:
     * 调用COllections.sort方法，通过定制排序比较两个Employee
     * （先按年龄比较，年龄相同按姓名比），使用
     * Lambda作为参数传递。
     */
    @Test
    public void test1() {
        Collections.sort(list, (x, y) -> {
            if (x.getAge() != y.getAge()) {
                return Integer.compare(x.getAge(), y.getAge());
            } else {
                return x.getName().compareTo(y.getName());
            }
        });
        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    /**
     * 需求2：
     * 1.声明函数式接口，接口中声明抽象方法，public String getvalue(String str();
     * 2.声明类TestLambda，类中编写方法使用接口作为参数，讲一个字符串转换成大写，并作为方法的返回值。
     */
    @Test
    public void test2() {
        String str = getvalue("hello world", x -> x.toUpperCase());
        System.out.print(str);

    }

    public String getvalue(String str, MyFunction1 my) {
        return my.getValue(str);
    }

    /**
     * @FunctionalInterface 主要用于编译级错误检查，加上该注解，当你写的接口不符合函数式接口定义的时候，编译器会报错
     * 函数式接口:接口内只有一个抽象方法,允许以下方法
     * 1.允许定义java.lang.Object里的public方法
     * 2.允许静态方法
     * 3.允许默认方法
     */
    @FunctionalInterface
    public interface MyFunction1 {
        String getValue(String str);
    }

    /**
     * 需求3：
     * 1.声明一个带两个泛型的函数式接口，泛型类型是<T,R>,T为参数，R为返回值。
     * 2.接口中声明对应抽象方法
     * 3.在TestLambda类中声明方法，使用接口作为参数，计算两个long型参数的和
     * 4.在计算两个long型参数的乘积
     */
    @Test
    public void test3() {
        Long r = getR(25L, 30L, (t1, t2) -> t1 * t2);
        System.out.println(r);

        Long r1 = getR(25L, 23L, (t1, t2) -> t1 + t2);
        System.out.println(r1);

    }

    public <T, R> R getR(T t1, T t2, MyFUnction2<T, R> mf) {
        return mf.method(t1, t2);
    }

    /**
     * Consumer<T> :消费型接口
     * void accept(T t);
     * <p>
     * Supplier<T> :供给型接口
     * T get();
     * <p>
     * Function<T,R> :函数型接口
     * R apply(T t);
     * <p>
     * Predicate<T> :断言型接口
     * boolean test(T t);
     *
     * @param <T>
     * @param <R>
     */
    @FunctionalInterface
    public interface MyFUnction2<T, R> {
        R method(T t1, T t2);
    }

}
