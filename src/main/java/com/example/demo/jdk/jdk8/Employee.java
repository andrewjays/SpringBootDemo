package com.example.demo.jdk.jdk8;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void main(String[] args) {

        Map<Integer, List<Integer>> mapmap = new HashMap<>();
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(1);
        objects.add(2);
        mapmap.put(1, null);
        mapmap.put(2, objects);
        mapmap.put(3, objects);


        mapmap.forEach((x, y) -> {
            if (y==null) {
                return;
            }
            y.forEach(z -> System.out.println(z));


        });

    }
}
