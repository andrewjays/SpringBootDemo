package com.example.demo.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Rate {
    private Integer aht;
    private Integer caseCnt;
    private String caseType;
    private String empNtId;
    private Date resolveDate;

}
