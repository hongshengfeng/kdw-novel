package com.keduw.service;

import com.keduw.model.Novel;
import com.keduw.util.Page;

import java.util.List;

public interface NovelService {

    //根据类别获取信息
    List<Novel> getNovelList(int curr, int size, int...category);

    //插入小说信息
    int insertNovel(Novel novel);

    //通过小说名字查找小说，可模糊查询
    Page<Novel> getNovelByName(String wd, int start);

    //通过novelId查找小说
    Novel getNovelById(int novelId);

    //最新小说
    List<Novel> getNewInfo();

    //热门小说
    List<Novel> getHotInfo();

    //判断小说是否存在或更新
    int isExitOrUpdate(Novel novel);

    //获取小说总数
    int getNovelCount();

    //获取某个类别的小说总数
    int getNovelCountByCategory(int category);

    //获取小说章节总数
    int getNovelSize(int id);
}
