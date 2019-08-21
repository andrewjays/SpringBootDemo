package com.example.demo.thread.semaphore;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 对象池
 *
 * @param <T>
 * @param <R>
 */
class ObjPool<T, R> {
    final List<T> pool;
    /**
     * 用信号量实现限流器
     */
    final Semaphore sem;

    /**
     * 构造函数
     *
     * @param size
     * @param t
     * @return
     */
    ObjPool(int size, T t) {
        // 需要用线程安全的vector，因为信号量支持多个线程进入临界区，
        // 执行list的add和remove方法时可能是多线程并发执行
        pool = new Vector<T>() {
        };
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        sem = new Semaphore(size);
    }

    /**
     * 利用对象池的对象，调用 func
     * <p>
     * 函数式接口
     */
    R exec(Function<T, R> func) throws InterruptedException {
        T t = null;
        sem.acquire();
        try {
            t = pool.remove(0);
            return func.apply(t);
        } finally {
            // 操作完成将对象放回池中
            // 同时将信号量+1
            pool.add(t);
            sem.release();
        }
    }
}

class test {
    public static void main(String[] args) throws InterruptedException {
        // 创建对象池
        ObjPool<Long, String> pool =
                new ObjPool<Long, String>(10, 2L);
        // 通过对象池获取 t，之后执行
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });

    }
}

