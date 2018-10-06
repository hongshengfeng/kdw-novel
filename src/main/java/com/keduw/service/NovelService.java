package com.keduw.service;

import com.keduw.model.Novel;

import java.util.List;

public interface NovelService {

    //根据类别获取信息
    List<Novel> getNovelList(int curr, int size, int...category);

    //插入小说信息
    int insertNovel(Novel novel);

    //通过小说名字查找小说，可模糊查询
    List<Novel> getNovelByName(String novelName);

    //通过novelId查找小说
    Novel getNovelById(int novelId);

    //最新小说
    List<Novel> getNewInfo();

    //热门小说
    List<Novel> getHotInfo();

    //判断小说是否存在或更新
    int isExitOrUpdate(Novel novel);
}
