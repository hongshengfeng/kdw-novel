package com.novel.dao;

import com.novel.model.Novel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.dao
 * @ClassName: NovelMapper
 * @Description: java类作用描述:小说信息持久层接口
 * @Author: 林浩东
 * @CreateDate: 2018/8/11/011 14:23
 * @UpdateUser: 林浩东
 * @UpdateDate: 2018/8/11/011 14:23
 * @UpdateRemark: 更新说明：无
 * @Version: 1.0
 */
@Mapper
public interface NovelMapper {
     /*插入小说信息*/
     void insertNovel(Novel novel);
     /*返回所有小说id*/
     List<Long> findAllNovelId();
     /*返回所有小说信息*/
     List<Novel> findAllNovel();
     /*通过小说名字查找小说，可模糊查询*/
     List<Novel> findNovelByName(String novelName);
     /*通过novelId查找小说*/
     Novel findNovelById(long novel);
     /*通过Novel实体查询Novel列表*/
     List<Novel> queryNovelList(Novel novel);

}
