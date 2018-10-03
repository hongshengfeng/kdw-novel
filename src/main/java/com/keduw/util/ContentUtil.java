package com.keduw.util;

import com.keduw.model.Chapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.utils
 * @ClassName: ContentUtil
 * @Description: java类作用描述：去掉表情符
 * @Author: 林浩东
 * @CreateDate: 2018/8/23/023 0:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/23/023 0:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ContentUtil {

    /**
     * 将emoji表情替换成空串
     *
     * @param source
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        } else {
            return source;
        }
    }

    public static List<Chapter> containChapter(List<Chapter> list, int size){
        int tmp = list.size() - size;
        List<Chapter> result = new ArrayList<>();
        for (int i = 1; i <=tmp ; i++) {
            result.add(list.get(size+i));
        }
        return result;
    }
}
