package com.keduw.logger;

import com.keduw.model.MsgInfo;
import com.keduw.model.Result;
import com.keduw.util.BaseUtil;
import com.keduw.util.JsonUtils;
import com.keduw.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * 全局统一处理异常
 * @author hsfeng
 */
@RestControllerAdvice
public class ExceptionHandle {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ExceptionHandle.class);

    //判断是否已定义的错误，不是则由未知错误代替
    //ajax请求的异常返回错误信息，页面请求异常调整到统一处理页面
    @ExceptionHandler(value = Exception.class)
    public Object exceptionGet(HttpServletRequest request, Exception exception){
        if(BaseUtil.isAjax(request)){
            LOGGER.error("error::", exception.getMessage());
            return ResultUtil.error(MsgInfo.UNKNOW_ERROR);
        }
        if(exception instanceof DescribeException){
            DescribeException e = (DescribeException) exception;
            Result error = ResultUtil.error(e.getCode(), e.getMessage());
            LOGGER.error("error::", JsonUtils.objectToJson(error));
        }else{
            LOGGER.error("error::", exception.getMessage());
        }
        ModelAndView view = new ModelAndView();
        view.setViewName("error");
        return view;
    }
}
