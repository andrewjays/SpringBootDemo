package com.example.demo.thread.forkjoin;

import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Jay
 * @description
 * @date Created in 2019/10/22 19:38
 */
//public class Fibonacci extends ForkJoinTask<T> {


//    public Fibonacci(int i) {
//
//    }
//
//    static void main(String[] args) {
//        //创建分治任务线程池
//        ForkJoinPool fjp =
//                new ForkJoinPool(4);
//        //创建分治任务
//        Fibonacci fib =
//                new Fibonacci(30);
//        //启动分治任务
//        T result =
//                fjp.invoke((ForkJoinTask<T>) fib);
//        //输出结果
//        System.out.println(result);
//    }
//
//    @Override
//    public T getRawResult() {
//        return null;
//    }
//
//    @Override
//    protected void setRawResult(T value) {
//
//    }
//
//    @Override
//    protected boolean exec() {
//        return false;
//    }
//
//    public Integer compute() {
//    }
//}
//
//
////递归任务
//class Fibonacci1 extends
//        RecursiveTask<Integer> {
//    final int n;
//
//    Fibonacci1(int n) {
//        this.n = n;
//    }
//
//    protected Integer compute() {
//        if (n <= 1)
//            return n;
//        Fibonacci f1 =
//                new Fibonacci(n - 1);
//        //创建子任务
//        f1.fork();
//        Fibonacci f2 =
//                new Fibonacci(n - 2);
//        //等待子任务结果，并合并结果
//        return f2.compute() + f1.join();
//    }
//}
