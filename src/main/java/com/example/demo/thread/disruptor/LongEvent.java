package com.example.demo.thread.disruptor;

/**
 * @author Jay
 * @description 自定义event
 * @date Created in 2019/11/9 14:05
 */
public class LongEvent {

    private long value;

    public void set(long value) {
        this.value = value;
    }
}
