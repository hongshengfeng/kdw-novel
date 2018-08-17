package com.novel.service;

import com.novel.model.Chapter;

import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service
 * @ClassName: ChapterService
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/8/12/012 1:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/12/012 1:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ChapterService {

     /*插入章节*/
     void insertChapter(Chapter chapter);

     /*更新章节列表*/
     void updateChapter(Chapter chapter);

     /*通过小说id返回章节列表*/
     List<Chapter> findByNovelIdChapter(long NovelId);
}
