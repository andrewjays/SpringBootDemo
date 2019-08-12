package com.example.demo.thread.test;

class Account1 {
    private Object lock;
    private int balance;

    /**
     * 初始化传入同一个锁对象
     *
     * @param lock
     */
    private Account1(Object lock) {
        this.lock = lock;
    }

    //    /**
    //     * 转账
    //     */
    //    synchronized void transfer(
    //            Account1 target, int amt) {
    //        // 此处检查所有对象共享的锁
    //        synchronized (Account1.class) {
    //            if (this.balance > amt) {
    //                this.balance -= amt;
    //                target.balance += amt;
    //            }
    //        }
    //    }


    /**
     * 转账
     */
    void transfer(Account1 target, int amt) {
        // 锁定转出账户
        synchronized (this) {
            // 锁定转入账户
            synchronized (target) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}






















