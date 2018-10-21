package com.keduw.dao;

import com.keduw.model.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.dao
 * @ClassName: ChapterMapper
 * @Description: java类作用描述:章节信息持久层接口
 * @Author: 林浩东
 * @Version: 1.0
 */
@Mapper
public interface ChapterMapper {
    //插入章节
    void insertChapter(List<Chapter> chapterList);

    //更新章节信息
    void updateChapter(Chapter info);

    //根据novelId返回章节信息
    List<Chapter> selectInfoByNovelId(Integer novelId);

    //获取章节总数
    int selectCounts();

    //获取content为空的章节列表
    List<Chapter> selectInfoByContent();

    String selectContentById(@Param("novelId")Integer novelId, @Param("chapterId")Integer chapterId);
}
