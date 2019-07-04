package com.example.demo.jdk.jdk8;

import lombok.Data;

/**
 * @author Jay
 * @description
 * @date Created in 2019/7/2 17:40
 */
@Data
public class Employee implements Comparable<Employee> {
    private String name;
    private String address;
    private int salary;
    private int age;
    public Employee() {

    }
    public Employee(String name, String address, int salary, int age) {
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.age = age;
    }

    @Override
    public int compareTo(Employee o) {
        if (age < o.getAge()) {
            return -1;
        } else {
            return 1;
        }

    }
}
