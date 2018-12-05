package com.keduw.util;

//参数校验工具类,传参整数接收均为字符串，做校验
public class Parser {

    public static int parserInt(String value, int dValue){
        try{
            int num = Integer.parseInt(value);
            return num;
        }catch (Exception e){
            return dValue;
        }
    }

    public static Long parserLong(String value, Long dValue){
        try{
            Long num = Long.parseLong(value);
            return num;
        }catch (Exception e){
            return dValue;
        }
    }

    public static Boolean parserBoolean(String value, boolean dValue){
        try{
            Boolean result = Boolean.parseBoolean(value);
            return result;
        }catch (Exception e){
            return dValue;
        }
    }

}
