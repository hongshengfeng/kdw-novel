package com.novel.service;

import com.novel.model.Novel;

import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service
 * @ClassName: NovelIService
 * @Description: java类作用描述:编写业务层接口
 * @Author: 林浩东
 * @CreateDate: 2018/8/11/011 14:41
 * @UpdateUser: 林浩东
 * @UpdateDate: 2018/8/11/011 14:41
 * @UpdateRemark: 更新说明：无
 * @Version: 1.0
 */

public interface NovelIService {

    void insertNovel(Novel novel);
    List<Long> findAllNovelId();
    List<Novel> findAllNovel();

}
