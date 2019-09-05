package com.example.demo.thread.aomic;

class SimulatedCAS {
    volatile static int count = 1;

    // 实现 count+=1
    public static void main(String[] args) {
        int newValue;
        do {
            newValue = count + 1; //①
            System.out.println(newValue);
        } while (count != cas(count, newValue));//②
    }


    /**
     * 模拟实现 CAS，仅用来帮助理解
     */
    synchronized static int cas(
            int expect, int newValue) {
        // 读目前 count 的值
        int curValue = count;
        // 比较目前 count 值是否 == 期望值
        if (curValue == expect) {
            // 如果是，则更新 count 的值
            count = newValue;
        }
        // 返回写入前的值
        return curValue;
    }
}
