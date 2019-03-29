package com.keduw.logger;

import java.lang.annotation.*;

/**
 * 日志处理拦截规则
 * @author hsfeng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

}
