package com.keduw.utils;

import com.keduw.model.MsgInfo;
import com.keduw.model.Result;

/**
 * 异常处理工具类
 * @author hsfeng
 */
public class ResultUtil {

    //成功，返回具体参数
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(object);
        return result;
    }

    //成功
    public static Result success(){
        return success(null);
    }

    //错误，自定义状态码和信息
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    //错误，返回异常信息
    public static Result error(MsgInfo msgInfo){
        Result result = new Result();
        result.setCode(msgInfo.getCode());
        result.setMsg(msgInfo.getMsg());
        result.setData(null);
        return result;
    }
}
