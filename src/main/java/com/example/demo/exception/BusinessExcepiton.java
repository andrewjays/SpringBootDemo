package com.example.demo.exception;

/**
 * Created by Administrator on 2018/8/1.
 */
public class BusinessExcepiton extends RuntimeException {
    private static final long serialVersionUID = -6375664354763349242L;

    private int status;
    private String msg;

    public BusinessExcepiton(int status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
    }

    public BusinessExcepiton() {
        super();
    }

    public BusinessExcepiton(String message) {
        super(message);
    }

    public BusinessExcepiton(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessExcepiton(Throwable cause) {
        super(cause);
    }

    protected BusinessExcepiton(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
