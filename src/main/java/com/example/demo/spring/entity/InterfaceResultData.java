package com.example.demo.spring.entity;

public class InterfaceResultData {
    /**
     * 状态编码code
     */
    private int status;
    /**
     * 状态对应值
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    public InterfaceResultData(int status, String msg, Object data) {
        super();
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public InterfaceResultData(int status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
    }

    public InterfaceResultData() {
        super();

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InterfaceResultData{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
