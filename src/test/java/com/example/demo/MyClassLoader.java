package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest()
public class MyClassLoader  {

    private int a =1;

    @Test
    public  void test1() {
        int b =2;

        test(b);
    }

    private   void test (int a){

        System.out.println(a);

    }

}
