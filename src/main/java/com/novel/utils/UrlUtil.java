package com.novel.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.utils
 * @ClassName: UrlUtil
 * @Description: java类作用描述：去掉空格换行
 * @Author: 林浩东
 * @CreateDate: 2018/8/11/011 23:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/11/011 23:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UrlUtil {

    public static String Urltrim(String url){
        if (url!=null){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(url);
            String nextUrl = m.replaceAll("");
            return nextUrl;
        }

        return null;
    }

}
