package com.keduw.model;

/**
 * 枚举异常信息
 * @author hsfeng
 */
public enum MsgInfo {
    SUCCESS(0, "查询成功"),
    UNKNOW_ERROR(-1, "系统错误"),
    USER_NOT_FOUND(-101, "用户不存在");

    private Integer code;
    private String msg;

    MsgInfo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
