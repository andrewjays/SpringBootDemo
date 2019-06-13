package com.example.demo.thread.volatiletest;

/**
 * @author Jay
 * @description 可见性
 * @date Created in 2019/6/13 12:42
 */
public class VolatileTest {

    public static volatile int finished=0;

    private  static  void  checkFinished(){

        while (finished==0){
            System.out.println("no finish");
        }

        System.out.println("finished");
    }

    private static  void  finish(){
        finished=1;
    }

    public static void main(String[] args) throws Exception{
        new Thread(()->checkFinished()).start();
        Thread.sleep(100);
        // 主线程将标志位置为1
        finish();
        System.out.println("main finished");

    }
}
