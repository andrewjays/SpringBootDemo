package com.example.demo.thread.synchronizedTest;

/**
 * @author Jay
 * @description
 * @date Created in 2019/8/9 17:21
 */
public class Test1 {
}

class Account3 {
    private int id;
    private int balance;

    // 转账
    void transfer(Account3 target, int amt) {
        Account3 left = this;        //①
        Account3 right = target;   // ②
        if (this.id > target.id) { //③
            left = target;          // ④
            right = this;           // ⑤
        }                          //⑥
        // 锁定序号小的账户
        synchronized (left) {
            // 锁定序号大的账户
            synchronized (right) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
