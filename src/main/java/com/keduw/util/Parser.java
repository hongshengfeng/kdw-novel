package com.keduw.util;

import org.springframework.stereotype.Component;

//参数校验工具类
public class Parser {

    /**
     * 传参整数接收均为字符串，做校验
     * @param value
     * @param defaultNum
     * @return
     */
    public static int parserInt(String value, int defaultNum){
        try{
            int num = Integer.parseInt(value);
            return num;
        }catch (Exception e){
            return defaultNum;
        }
    }

}
