package com.example.demo.thread.countDownLatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest03 {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        final CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            check();
        });
        checkAll(barrier);
    }

    public static void check() {
        System.out.println("开始对账 线程名称为" + Thread.currentThread().getName());
    }

    public static void checkAll(CyclicBarrier barrier) {
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("<--------查询订单库 线程名称为" + Thread.currentThread().getName());
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

        });
        t1.start();

        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("-------->查询运单库 线程名称为" + Thread.currentThread().getName());
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }
}