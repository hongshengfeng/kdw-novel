package com.novel.utils;

import org.jsoup.helper.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.utils
 * @ClassName: AuthorUtil
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/8/26/026 0:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/26/026 0:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AuthorUtil {

    public static String tirmAuthor(String author){
        String result ="";
        if(!StringUtil.isBlank(author)){
            List<String>  tmp = Arrays.asList(author.split(":|/"));
            if(tmp.size()>0){
                result =tmp.get(tmp.size()-1);
            }
        }

        return result;
    }
}
