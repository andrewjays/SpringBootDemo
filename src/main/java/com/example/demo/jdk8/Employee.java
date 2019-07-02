package com.example.demo.jdk8;

import lombok.Data;

/**
 * @author Jay
 * @description
 * @date Created in 2019/7/2 17:40
 */
@Data
public class Employee {
    private String name;
    private String address;
    private int money;
    private int age;

    public Employee(String name, String address, int money, int age) {
        this.name = name;
        this.address = address;
        this.money = money;
        this.age = age;
    }

}
