package com.example.demo.springcontext;

import org.springframework.stereotype.Component;

/**
 * @author Jay
 * @description
 * @date Created in 2019/6/27 16:46
 */
@Component
public class ContextTest1Impl implements ContextTest1 {
    @Override
    public void test() {
        System.out.println("<<-------------------接口测试");
    }
}
