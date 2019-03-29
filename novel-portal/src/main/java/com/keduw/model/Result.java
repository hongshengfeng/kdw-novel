package com.keduw.model;

/**
 * 返回体的报文，日志处理
 * @author hsfeng
 */
public class Result<T> {

    private int code;   //状态值
    private String msg; //错误信息,若code为0，则为success
    private T data;     //返回的内容

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
