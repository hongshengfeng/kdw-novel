package com.keduw.service.impl;

import com.keduw.dao.ChapterMapper;
import com.keduw.jedis.JedisClient;
import com.keduw.model.Chapter;
import com.keduw.service.ChapterService;
import com.keduw.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service.impl
 * @ClassName: ChapterServiceImpl
 * @Description: 小说信息
 * @Author: 林浩东
 */
@Service("chapterService")
@PropertySource("classpath:cache.properties")
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("chapter")
    private String keys;

    //插入章节（后续分表在这里操作）
    @Override
    public void insertChapter(Chapter chapter) {
        chapterMapper.insertChapter(chapter);
    }


    //更新章节列表（后续分表在这里操作）
    @Override
    public void updateChapter(Chapter chapter) {
        chapterMapper.updateChapter(chapter);
    }


    //通过小说id返回章节列表
    @Override
    public List<Chapter> findByNovelIdChapter(long NovelId) {
        List<Chapter> list = new ArrayList<Chapter>();
        String field = "chapter" + NovelId;
        String info = jedisClient.hget(keys, field);
        if(info != null && !info.isEmpty()){
            list = JsonUtils.jsonToList(info, Chapter.class);
        }else{
            list = chapterMapper.selectInfoByNovelId(NovelId);
            //添加数据到redis中
            jedisClient.hset(keys, field, JsonUtils.objectToJson(list));
        }
        return list;
    }

}
