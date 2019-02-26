package com.example.demo.constant;

/**
 * @author zhounan
 * Created by Administrator on 2018/8/13.
 */

public enum ResultMsg {
    /**
     * 状态码定义
     */
    SUCCESS_1000(1000, "成功", "success"),

    ERROR_2001(2001, "暂无新任务", "No new task available"),
    ERROR_2002(2002, "车辆未注册", "Unregistered vehicle"),
    ERROR_2003(2003, "VIN不存在", "VIN does not exist"),
    ERROR_2004(2004, "VIN包含特殊字符", "VIN contains special characters"),
    ERROR_2005(2005, "VIN长度错误", "VIN length error"),
    ERROR_2006(2006, "重新注册", "Re-registration"),

    ERROR_3001(3001, "配置信息需要更新", "Configuration information needs to be updated"),
    ERROR_3002(3002, "配置信息不存在", "Configuration information does not exist"),

    ERROR_4001(4001, "没有最新诊断任务", "No latest diagnostic task"),

    ERROR_5001(5001, "JSON解析异常", "JSON parsing exception"),
    ERROR_5002(5002, "参数校验失败", "Parameter check failed"),
    ERROR_5003(5003, "签名验证失败", "Signature verification failed"),
    ERROR_5004(5004, "文件保存异常", "File save exception"),
    ERROR_5005(5005, "参数缺失", "Parameter deletion"),
    ERROR_5006(5006, "任务ID不存在", "Task ID does not exist"),
    ERROR_5007(5007, "脚本ID不存在", "Script ID does not exist"),

    ERROR_6001(6001, "执行状态值非法", "Execution state value illegal"),

    ERROR_7001(7001, "数据上报类型非法", "Data reporting type illegal"),
    ERROR_7002(7002, "文件为空", "File is null"),
    ERROR_7003(7003, "仅支持zip文件", "Only zip files supported"),
    ERROR_7004(7004, "文件上传失败", "File upload failed"),

    ERROR_8001(8001, "脚本状态值非法", "Script state value illegal"),

    ERROR_10000(10000, "系统的异常信息", "Server internal exception");

    private int code;
    private String mgszh;
    private String mgsen;

    ResultMsg(int code, String mgszh, String mgsen) {
        this.code = code;
        this.mgszh = mgszh;
        this.mgsen = mgsen;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMgszh() {
        return mgszh;
    }

    public void setMgszh(String mgszh) {
        this.mgszh = mgszh;
    }

    public String getMgsen() {
        return mgsen;
    }

    public void setMgsen(String mgsen) {
        this.mgsen = mgsen;
    }
}
