package com.example.demo.thread.volatiletest;

import java.util.stream.Stream;

/**
 * @author Jay
 * @description
 * @date Created in 2019/6/13 12:53
 */
public class TestVolatile {
    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args)throws Exception {
        final TestVolatile test = new TestVolatile();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 1000; j++)
                    test.increase();
            });
            t.start();
            t.join();
        }

                while(Thread.activeCount()>1){
                    Thread.yield();
                }


        System.out.println(test.inc);


    }


}
