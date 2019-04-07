package com.keduw.service;


import com.keduw.model.Chapter;

import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service
 * @ClassName: ChapterService
 * @Description: java类作用描述
 * @Author: 林浩东
 */
public interface ChapterService {

     //插入章节
     void insertChapter(List<Chapter> chapterList);

     //更新章节列表
     void updateChapter(List<Chapter> chapterList);

     //更新章节内容
     void updateChapterContent(Chapter chapter);

     //查询章节内容为空的数据
     List<Chapter> getChapterList(int start, int size);

     //获取章节总数
     int getInfoCounts();

}
