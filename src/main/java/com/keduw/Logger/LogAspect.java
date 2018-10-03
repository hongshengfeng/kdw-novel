package com.keduw.Logger;

import com.keduw.model.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志处理
 * @author hsfeng
 */
@Component
@Aspect
public class LogAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private ExceptionHandle handle;

    @Pointcut("execution(public * com.keduw.controller.*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOGGER.info("url={}",request.getRequestURL()); //url
        LOGGER.info("method={}",request.getMethod()); //method
        LOGGER.info("id={}",request.getRemoteAddr()); //ip
        LOGGER.info("args={}",joinPoint.getArgs()); //args[]
    }

    //捕获异常
    @Around("log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Result result = null;
        try {

        } catch (Exception e) {
            return handle.exceptionGet(e);
        }
        if(result == null){
            return proceedingJoinPoint.proceed();
        }else {
            return result;
        }
    }

    //打印输出结果
    @AfterReturning(pointcut = "log()",returning = "object")
    public void doAfterReturing(Object object){
        LOGGER.info("response={}",object.toString());
    }

}
