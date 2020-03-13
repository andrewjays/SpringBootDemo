package com.example.demo.spring.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zhounan
 * @Description: 该aop用于请求结束时, 清楚该线程的threadLocal中的值
 * @create 2019-01-30 15:45
 */
@Aspect
@Component
public class ThreadLocalAop {

    /**
     * 定义AOP扫描路径
     */
    // @Pointcut("execution(* com.example.demo.spring.controller..*.*(..))")
    public void log() {

    }


    /**
     * 获取返回内容
     *
     * @param object
     */
    //@AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturn(Object object) {

        System.out.println("aop测试：" + object);

    }
}
