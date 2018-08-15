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

     void insertNovel(Novel novel);

     List<Long> findAllNovelId();
     List<Novel> findAllNovel();


}
