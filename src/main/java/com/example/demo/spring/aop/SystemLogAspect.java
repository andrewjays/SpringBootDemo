package com.example.demo.spring.aop;

import com.example.demo.spring.myannotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Jay
 * @description 日志切面
 * @date Created in 2020/3/13 11:55 上午
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {
    /**
     * 把注解作为切点
     */
    @Pointcut("@annotation(com.example.demo.spring.myannotation.Log)")
    public void controllerAspect() {
    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("==========执行controller前置通知===============");
        if (log.isInfoEnabled()) {
            log.info("before " + joinPoint);
        }
    }

    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around("controllerAspect()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("==========开始执行controller环绕通知===============");
        try {
            // 执行目标切点方法，返回值与该方法返回值一致
            proceedingJoinPoint.proceed();
            System.out.println("==========结束执行controller环绕通知===============");
        } catch (Throwable throwable) {
            throwable.printStackTrace();

        }

    }

    /**
     * 后置方法
     *
     * @param joinPoint
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {

        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();

            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();

            String operationType = "";
            String operationName = "";

            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operationType = method.getAnnotation(Log.class).operationType();
                        operationName = method.getAnnotation(Log.class).operationName();
                        break;
                    }
                }
            }
            System.out.println(operationType+"-----------"+operationName);



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置后置返回通知,使用在方法aspect()上注册的切入点
     */
    @AfterReturning("controllerAspect()")
    public void afterReturn(JoinPoint joinPoint) {
        System.out.println("=====执行controller后置返回通知=====");
        if (log.isInfoEnabled()) {
            log.info("afterReturn " + joinPoint);
        }
    }

}
