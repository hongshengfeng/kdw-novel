package com.keduw.service;

import com.keduw.model.Novel;

import java.util.List;

public interface NovelService {

    //根据类别获取信息
    List<Novel> getNovelList(int curr, int size, int...category);

    //插入小说信息
    void insertNovel(Novel novel);

    //通过小说名字查找小说，可模糊查询
    List<Novel> getNovelByName(String novelName);

    //通过novelId查找小说
    Novel getNovelById(long novelId);

    //获取所有小说
    List<Novel> getAllNovelInfo();
}
