package com.example.demo.thread.readWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 锁的升降级
 */
public class CachedData {
    Object data;
    private volatile boolean cacheValid;
    private final ReadWriteLock rwl =
            new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    private final Lock r = rwl.readLock();
    /**
     * 写锁
     */
    private final Lock w = rwl.writeLock();

    void processCachedData() {
        // 获取读锁
        r.lock();
        if (!cacheValid) {
            // 释放读锁，因为不允许读锁的升级
            r.unlock();
            // 获取写锁
            w.lock();
            try {
                // 再次检查状态
                if (!cacheValid) {
                    // data = ...
                    cacheValid = true;
                }
                // 释放写锁前，降级为读锁
                // 降级是可以的
                r.lock();
            } finally {
                // 释放写锁
                w.unlock();
            }
        }
        // 此处仍然持有读锁
        try {
            // use(data);
        } finally {
            r.unlock();
        }
    }
}
