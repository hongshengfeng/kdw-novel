package com.keduw.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    //判断是否为手机浏览器
    public static boolean isMoblie(HttpServletRequest request) {
        boolean isMoblie = false;
        String[] mobileAgents = { "iphone", "android","ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile" };
        if (request.getHeader("User-Agent") != null) {
            String agent=request.getHeader("User-Agent");
            for (String mobileAgent : mobileAgents) {
                if (agent.toLowerCase().indexOf(mobileAgent) >= 0 && agent.toLowerCase().indexOf("windows nt") <= 0 && agent.toLowerCase().indexOf("macintosh") <= 0) {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;
    }

}
