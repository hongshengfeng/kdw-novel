package com.keduw.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: novelSpider
 * @Package: com.keduw.util
 * @ClassName: ChapterUtil
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/11/12/012 22:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/12/012 22:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ChapterUtil {
    public static  final int TIME = 60*60*24*30;
    public static String CHAPTERID = "chapterId";

    /*保存浏览记录*/
    public static void saveChapterId(HttpServletRequest request, HttpServletResponse response,long chapterId){
        boolean flag = false;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if(cookie.getName().equals(CHAPTERID)){
                cookie.setMaxAge(TIME);
                cookie.setValue(String.valueOf(chapterId));
                response.addCookie(cookie);
                flag = true;
                break;
            }
        }
        if(!flag){
            Cookie cookie = new Cookie(CHAPTERID,String.valueOf(chapterId));
            cookie.setMaxAge(TIME);
            response.addCookie(cookie);
        }


    }

}
