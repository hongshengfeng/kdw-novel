package com.keduw.Logger;

import com.keduw.model.MsgInfo;
import com.keduw.model.Result;
import com.keduw.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局统一处理异常
 * @author hsfeng
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(ExceptionHandle.class);

    //判断是否已定义的错误，不是则由未知错误代替
    @ExceptionHandler(value = Exception.class)
    public Result exceptionGet(Exception exception){
        if(exception instanceof DescribeException){
            DescribeException e = (DescribeException) exception;
            return ResultUtil.error(e.getCode(), e.getMessage());
        }

        LOGGER.error("error:", exception);
        return ResultUtil.error(MsgInfo.UNKNOW_ERROR);
    }
}
