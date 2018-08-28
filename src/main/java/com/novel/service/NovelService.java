package com.novel.service;

import cn.edu.hfut.dmic.webcollector.model.Page;
import com.github.pagehelper.PageInfo;
import com.novel.model.Novel;

import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service
 * @ClassName: NovelService
 * @Description: java类作用描述:编写业务层接口
 * @Author: 林浩东
 * @CreateDate: 2018/8/11/011 14:41
 * @UpdateUser: 林浩东
 * @UpdateDate: 2018/8/11/011 14:41
 * @UpdateRemark: 更新说明：无
 * @Version: 1.0
 */

public interface NovelService {

    /*插入小说信息*/
    void insertNovel(Novel novel);
    /*返回所有小说id*/
    List<Long> findAllNovelId();
    /*返回所有小说*/
    List<Novel> findAllNovel();
    /*通过小说名字查找小说，可模糊查询*/
    List<Novel> findNovelByName(String novelName);
    /*通过novelId查找小说*/
    Novel findNovelById(long novelId);
    /*通过Novel实体查询Novel列表*/
    List<Novel> queryNovelList(Novel novel);

    PageInfo<Novel> selectAll(Integer page, Integer size);

}
