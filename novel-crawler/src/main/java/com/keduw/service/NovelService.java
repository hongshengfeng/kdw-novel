package com.keduw.service;

import com.keduw.model.Novel;
import com.keduw.utils.Page;

import java.util.List;

public interface NovelService {

    //根据类别获取信息
    List<Novel> getNovelList(int curr, int size, int... category);

    //插入小说信息
    int insertNovel(Novel novel);

    //判断小说是否存在或更新
    int isExitOrUpdate(Novel novel);

    //获取小说总数
    int getNovelCount();

    //获取小说章节总数
    int getNovelSize(int id);
}
