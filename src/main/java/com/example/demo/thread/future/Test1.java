package com.example.demo.thread.future;


import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Jay
 * @description submit 方法
 * @date Created in 2019/10/19 12:03
 */
public class Test1 {
    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        // 创建result对象
        Result r = new Result();
        r.setMsg("testMsg");
        // 任务提交
        Future<Result> submit = executor.submit(new Task(r), r);
        // result 与 r 相等,完成子线程与主线程通信
        Result result = submit.get();

    }
}


/**
 * Result 对象
 */
@Data
class Result {
    private String msg;
    private String code;

}

class Task implements Runnable {

    Result r;

    // 通过构造函数传入
    Task(Result r) {
        this.r = r;
    }

    @Override
    public void run() {
        // 操作result
        String msg = r.getMsg();
        r.setCode("testCode");
    }
}


