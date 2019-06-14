package com.example.demo.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Data
@Component
public class BRO {
    private RiskFoundRate riskFoundRate;

    @PostConstruct
    public void test() {

        System.out.println("---------------------->>>>>>>>>项目启动");
    }

}
