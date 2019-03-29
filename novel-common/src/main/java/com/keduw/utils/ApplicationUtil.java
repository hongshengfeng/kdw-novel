package com.keduw.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * java中普通类调用Spring bean对象
 */
@Component
public class ApplicationUtil implements ApplicationContextAware{

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationUtil.applicationContext == null){
            ApplicationUtil.applicationContext  = applicationContext;
        }
    }

    //通过name获取Bean.
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

}