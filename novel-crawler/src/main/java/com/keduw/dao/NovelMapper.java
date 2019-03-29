package com.keduw.dao;

import com.keduw.model.Novel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * java类作用描述:小说信息持久层接口
 * @Author: 林浩东
 */
@Mapper
public interface NovelMapper {

     //插入小说信息
     int insertNovel(Novel novel);

     //查询所有小说的列表
     List<Novel> selectNovel(@Param("curr") int curr, @Param("size") int size);

     //根据类别查询小说列表
     List<Novel> selectNovelByCategory(@Param("categoryId") int categoryId, @Param("curr") int curr, @Param("size") int size);


     //根据名字和作者获取小说的基础信息
     Novel selectInfoByName(@Param("name") String name, @Param("author") String author);

     //查询小说的总数
     int selectInfoCount();

     //查询小说章节总数
     int selectSizeById(int id);
}
