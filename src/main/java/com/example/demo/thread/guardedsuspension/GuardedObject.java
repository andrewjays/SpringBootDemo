package com.example.demo.thread.guardedsuspension;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class GuardedObject<T> {

    /**
     * 受保护的对象
     */
    T obj;
    /**
     * 可重入锁
     */
    final Lock lock =
            new ReentrantLock();
    final Condition done =
            lock.newCondition();
    final int timeout = 1;

    /**
     * 获取受保护对象
     */
    T get(Predicate<T> p) {
        lock.lock();
        try {
            //MESA管程推荐写法
            while (!p.test(obj)) {
                done.await(timeout,
                        TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        //返回非空的受保护对象
        return obj;
    }

    /**
     * 事件通知方法
     */
    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) throws Exception {

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        ses.scheduleWithFixedDelay(() -> System.out.println("定时任务测试1"), 0,1L, TimeUnit.SECONDS);
        ses.scheduleWithFixedDelay(() -> System.out.println("定时任务测试2"),0, 1L, TimeUnit.SECONDS);
        ses.scheduleWithFixedDelay(() -> System.out.println("定时任务测试3"),0, 1L, TimeUnit.SECONDS);

    }
}
