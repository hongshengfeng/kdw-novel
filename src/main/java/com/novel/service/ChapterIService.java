package com.novel.service;

import com.novel.model.Chapter;

import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service
 * @ClassName: ChapterIService
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/8/12/012 1:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/12/012 1:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ChapterIService {

     void insertChapter(Chapter chapter);
     List<Chapter> findByNovelIdChapter(long NovelId);
     void updateChapter(Chapter chapter);
}
