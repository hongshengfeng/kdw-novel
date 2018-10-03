package com.keduw.dao;

import com.keduw.model.Novel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * java类作用描述:小说信息持久层接口
 * @Author: 林浩东
 */
@Mapper
public interface NovelMapper {

     //插入小说信息
     void insertNovel(Novel novel);

     //通过小说名字查找小说，可模糊查询
     List<Novel> selectNovelByName(String novelName);

     //通过novelId查找小说
     Novel selectNovelById(long novel);

     //查询所有小说的列表
     List<Novel> selectNovel();

     //根据类别查询小说列表
     List<Novel> selectNovelByCategory(int categoryId);

     //查询所有的小说列表
     List<Novel> seletAllNovelInfo();

     //查询最新小说列表
     List<Novel> seletNewNovelInfo();

     //查询热门小说列表
     List<Novel> seletHotNovelInfo();
}
