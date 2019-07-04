package com.example.demo.jdk.jdk8;

import org.junit.Test;

import java.util.Optional;

/**
 * @author Jay
 * @description Optional 类(java.util.Optional) 是一个容器类，代表一个值存在或不存在，
 * 原来用null 表示一个值不存在，现在Optional 可以更好的表达这个概念。并且可以避免空指针异常。
 * @date Created in 2019/7/4 10:35
 */
public class OptionalTest {
    /**
     * 常用方法：
     Optional.of(T t) : 创建一个Optional 实例
     Optional.empty() : 创建一个空的Optional 实例
     Optional.ofNullable(T t):若t 不为null,创建Optional 实例,否则创建空实例
     isPresent() : 判断是否包含值
     orElse(T t) : 如果调用对象包含值，返回该值，否则返回t
     orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回s 获取的值
     map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
     flatMap(Function mapper):与map 类似，要求返回值必须是Optional
     */
    /**
     * flatMap
     */
    @Test
    public void test1() {
        Optional<Employee> op = Optional.ofNullable(new Employee("jim", "shanghai", 9000, 23));
        // Optional<Employee> op = Optional.ofNullable(null);
        // flatMap，返回的必须是Optional容器，进一步避免空指针异常
        Optional<Integer> optional = op.flatMap(e -> Optional.of(e.getSalary()));
        System.out.println(optional.orElse(10000));

    }

    /**
     * map
     */
    @Test
    public void test2() {
        // Optional<Employee> op = Optional.ofNullable(new Employee("jim","shanghai",9000));
        Optional<Employee> op = Optional.ofNullable(null);
        // 有值返回map的操作，没值返回Optional.empty()
        Optional<Integer> salary = op.map(Employee::getSalary);
        // 如果传入为空，此时会报错
        System.out.println(salary.get());

    }

    /**
     * orElse
     * orElseGet
     */
    @Test
    public void test3() {
        Optional<Object> op = Optional.ofNullable(null);
        if (op.isPresent()) {
            System.out.println(op.get());
        } else {
            // 如果没值，传入默认的值
            op.orElse(new Employee());
            // 函数式接口，可以写更多
            Object o = op.orElseGet(Employee::new);
            System.out.println(o);
        }
    }


    /**
     * ofnullable
     */
    @Test
    public void test4() {
        Optional<Object> op = Optional.ofNullable(null);
        if (op.isPresent()) {
            // 仍然会报错，NoSUchELEMEnt.exception}
            System.out.println(op.get());
        } else {
            System.out.println("No Value");
        }
    }

    /**
     * empty: 创建一个空的Optional 实例
     */
    @Test
    public void test5() {
        Optional<Object> op = Optional.empty();
        // 也会报错，NoSuchElement.Exception
        System.out.println(op.get());
    }

    /**
     * of 构建
     */
    @Test
    public void test6() {
        Optional<Employee> op = Optional.of(new Employee());
        Employee employee = op.get();
        System.out.println(employee);
        //Optional<Object> op2 = Optional.of(null);//直接传null会发生空指针异常
        // Object o = op2.get();
        // System.out.println(o);
    }
}
