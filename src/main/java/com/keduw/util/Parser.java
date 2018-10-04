package com.keduw.util;

import org.springframework.stereotype.Component;

//参数校验工具类,传参整数接收均为字符串，做校验
public class Parser {

    public static int parserInt(String value, int defaultNum){
        try{
            int num = Integer.parseInt(value);
            return num;
        }catch (Exception e){
            return defaultNum;
        }
    }

    public static Long parserLong(String value, Long defaultNum){
        try{
            Long num = Long.parseLong(value);
            return num;
        }catch (Exception e){
            return defaultNum;
        }
    }

}
