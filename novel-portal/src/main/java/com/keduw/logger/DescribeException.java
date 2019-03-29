package com.keduw.logger;

import com.keduw.model.MsgInfo;

/**
 * 自定义错误信息，除了404，500，400之外定义更细致
 * @author hsfeng
 */
public class DescribeException extends RuntimeException{

    private int code;

    public DescribeException(MsgInfo msgInfo){
        super(msgInfo.getMsg());
        this.code = msgInfo.getCode();
    }

    //自定义错误信息
    public DescribeException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
