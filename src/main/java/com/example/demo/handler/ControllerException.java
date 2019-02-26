package com.example.demo.handler;


import com.example.demo.constant.ResultMsg;
import com.example.demo.entity.InterfaceResultData;
import com.example.demo.exception.BusinessExcepiton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:zhounan
 * @Description: 异常处理类
 * @Date: Created in 2019/1/24 17:31
 */
@Slf4j
@ControllerAdvice
public class ControllerException {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public InterfaceResultData exception(Exception e) {
        e.printStackTrace();
        log.info("global error{}", e.getMessage());
        return new InterfaceResultData(ResultMsg.ERROR_10000.getCode(), ResultMsg.ERROR_10000.getMgszh());
    }


    @ExceptionHandler(value = BusinessExcepiton.class)
    @ResponseBody
    public InterfaceResultData businessExcepiton(BusinessExcepiton e) {
        e.printStackTrace();
        log.info("global error{}", e.getMessage());
        return new InterfaceResultData(e.getStatus(), e.getMsg());
    }
}
