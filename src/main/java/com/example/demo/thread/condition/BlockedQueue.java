package com.example.demo.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 队列
 *
 * @param <T>
 */
public class BlockedQueue<T> {
    private int a;
    final Lock lock =
            new ReentrantLock();
    /**
     * 条件变量：队列不满
     */
    final Condition notFull =
            lock.newCondition();
    /**
     * 条件变量：队列不空
     */
    final Condition notEmpty =
            lock.newCondition();

    /**
     * 入队
     */
    void enq(T x) {
        lock.lock();
        try {
            while (a > 1) {
                // 等待队列不满
                notFull.await();
                notFull.signalAll();
            }
            // 省略入队操作...
            // 入队后, 通知可出队
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 出队
     */
    void deq() {

        lock.lock();
        try {
            while (a < 1) {
                // 等待队列不空
                notEmpty.await();
            }
            // 省略出队操作...
            // 出队后，通知可入队
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
