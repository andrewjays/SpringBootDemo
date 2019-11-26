package com.example.demo.spring.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class BRO {
    private RiskFoundRate riskFoundRate;

    @PostConstruct
    public void test() {
       // TODO
        System.out.println("---------------------->>>>>>>>>项目启动");
    }

}
