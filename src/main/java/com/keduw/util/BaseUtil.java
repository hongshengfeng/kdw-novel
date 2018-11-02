package com.keduw.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//基础工具类，代码不长可以写在这里面
public class BaseUtil {

    /**
     * 生产随机小说novelId
     * @return
     */
    public static String createId(){
        String uuid = UUID.randomUUID().toString();
        String id = uuid.substring(0, uuid.indexOf("-"));
        return id;
    }

    /**
     * 以":"作为分隔符，取后面字段
     * @param str
     * @return
     */
    public static String tirmStr(String str){
        String result ="";
        if(str != null && !str.isEmpty()){
            List<String> tmp = Arrays.asList(str.split("：|/"));
            if(tmp.size()>0){
                result =tmp.get(tmp.size() -1 );
            }
        }
        return result;
    }

    /**
     * 过滤空格和回车
     * @param url
     * @return
     */
    public static String urlTrim(String url){
        String newUrl = "";
        if (url!=null && !url.isEmpty()){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(url);
            newUrl = m.replaceAll("");
        }
        return newUrl;
    }

    /**
     * 获取随机数
     * @param num
     * @return
     */
    public static int betweenRandom(int num){
        int result = (int) (Math.round(Math.random() * num));
        return result;
    }

    /**
     * 判断是否为ajax发起的请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request){
        boolean result = false;
        if(request.getHeader("X-Requested-With") == null){
            return result;
        }
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString());
    }
}
