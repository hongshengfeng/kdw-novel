package com.keduw.dao;

import com.keduw.model.Chapter;
import org.apache.ibatis.annotations.Mapper;

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
    void insertChapter(Chapter chapter);

    //更新章节信息
    void updateChapter(Chapter chapter);

    //根据novelId返回章节信息
    List<Chapter> selectInfoByNovelId(long NovelId);

}
