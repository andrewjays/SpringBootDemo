package com.example.demo.thread.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Jay
 * @description
 * @date Created in 2019/10/19 14:45
 */
public class FutureTest {

    public static void main(String[] args) {
        // 任务 1：洗水壶 -> 烧开水
        CompletableFuture<Void> f1 =
                CompletableFuture.runAsync(() -> {
                    System.out.println("T1: 洗水壶...");
                    sleep(1, TimeUnit.SECONDS);

                    System.out.println("T1: 烧开水...");
                    sleep(15, TimeUnit.SECONDS);
                });
        // 任务 2：洗茶壶 -> 洗茶杯 -> 拿茶叶
        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("T2: 洗茶壶...");
                    sleep(1, TimeUnit.SECONDS);

                    System.out.println("T2: 洗茶杯...");
                    sleep(2, TimeUnit.SECONDS);

                    System.out.println("T2: 拿茶叶...");
                    sleep(1, TimeUnit.SECONDS);
                    return " 龙井 ";
                });
        // 任务 3：任务 1 和任务 2 完成后执行：泡茶
        CompletableFuture<String> f3 =
                f1.thenCombine(f2, (__, tf) -> {
                    System.out.println("T1: 拿到茶叶:" + tf);
                    System.out.println("T1: 泡茶...");
                    return " 上茶:" + tf;
                });
        // 等待任务 3 执行结果
        System.out.println(f3.join());


    }

    static void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 串行关系
     */
    @Test
    public void test() {
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> "Hello World")
                .thenApply(s -> s + " zz")
                .thenApply(String::toUpperCase);
        System.out.println(f0.join());

    }

    /**
     * or汇聚关系 只要有一个异步返回则判定返回
     */
    @Test
    public void test1() {

        CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(() -> {
                    int t = 11;
                    sleep(t, TimeUnit.SECONDS);
                    return String.valueOf(t);
                });

        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> {
                    int t = 12;
                    sleep(t, TimeUnit.SECONDS);
                    return String.valueOf(t);
                });

        CompletableFuture<String> f3 =
                f1.applyToEither(f2, s -> s);

        System.out.println(f3.join());

    }

    /**
     * and汇聚关系 必须同时异步返回才判定返回
     */
    @Test
    public void test2() {

        CompletableFuture<Integer> f1 =
                CompletableFuture.supplyAsync(() -> {
                    int t = 2;
                    sleep(t, TimeUnit.SECONDS);
                    return t;
                });

        CompletableFuture<Integer> f2 =
                CompletableFuture.supplyAsync(() -> {
                    int t = 3;
                    sleep(t, TimeUnit.SECONDS);
                    return t;
                });

        CompletableFuture<Integer> f3 =
                f1.thenCombine(f2, (a, b) -> a + b);

        System.out.println(f3.join());

    }

    /**
     * 异常处理
     */
    @Test
    public void test3() {
        CompletableFuture<Integer>
                f0 = CompletableFuture
                .supplyAsync(() -> (7 / 0))
                .thenApply(r -> r * 10)
                .exceptionally(e -> 0);
        System.out.println(f0.join());


    }
}

