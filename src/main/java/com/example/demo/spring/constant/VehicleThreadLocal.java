package com.example.demo.spring.constant;


/**
 * 打印log相关的设置
 *
 * @author zhounan
 * @create 2019-01-04 15:54
 */


public class VehicleThreadLocal {


    public static final ThreadLocal<String> vin = new ThreadLocal<String>();

    public static final ThreadLocal<String> deviceId = new ThreadLocal<String>();

    public static final ThreadLocal<Integer> isTest = new ThreadLocal<Integer>();

    public static final ThreadLocal<String> sessionId = new ThreadLocal<String>();


}
