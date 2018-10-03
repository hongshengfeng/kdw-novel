package com.keduw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.keduw.dao.NovelMapper;
import com.keduw.jedis.JedisClient;
import com.keduw.model.Novel;
import com.keduw.service.NovelService;
import com.keduw.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.service.impl
 * @ClassName: NovelServiceImpl
 * @Description: java类作用描述
 * @Author: 林浩东
 */
@Service("novelService")
@PropertySource("classpath:cache.properties")
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelMapper novelMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("novel_list")
    private String novelList;
    @Value("novel_info")
    private String novelInfo;

    //分页获取小说的信息,category 类别,不传递则不分类
    @Override
    public List<Novel> getNovelList(int curr, int size, int...category) {
        List<Novel> list = new ArrayList<Novel>();
        StringBuilder fields = new StringBuilder();
        fields.append("novelList");
        fields.append(curr);
        if(category.length > 0){
            fields.append(category[0]);
        }
        String info = jedisClient.hget(novelList, fields.toString());
        if(info != null && !info.isEmpty()){
            list = JsonUtils.jsonToList(info, Novel.class);
            return list;
        }
        //redis数据不存在，查询后加入缓存
        PageHelper.startPage(curr, size);
        if(category.length == 0){
            list = novelMapper.selectNovel();
        }else{
            list = novelMapper.selectNovelByCategory(category[0]);
        }
        jedisClient.hset(novelList, fields.toString(), JsonUtils.objectToJson(list));
        return list;
    }

    //插入小说信息
    @Override
    public void insertNovel(Novel novel) {
        novelMapper.insertNovel(novel);
        //清除原有的redis缓存
        jedisClient.del(novelList);
    }

    //通过关键字查询小说
    @Override
    public List<Novel> getNovelByName(String novelName) {
        return novelMapper.selectNovelByName(novelName + "%");
    }

    //通过novelId查找小说
    @Override
    public Novel getNovelById(long novelId) {
        Novel novel = new Novel();
        String fields = "novel" + novel;
        String info = jedisClient.hget(novelInfo, fields);
        if(info != null && !info.isEmpty()){
            novel = JsonUtils.jsonToPojo(info, Novel.class);
        }else{
            novel = novelMapper.selectNovelById(novelId);
            jedisClient.hset(novelInfo, fields, JsonUtils.objectToJson(novel));
        }
        return novel;
    }

    //获取所有的小说列表
    @Override
    public List<Novel> getAllNovelInfo() {
        return novelMapper.seletAllNovelInfo();
    }

    //最新小说
    @Override
    public List<Novel> getNewInfo() {
        List<Novel> list = new ArrayList<>();
        String field = "newInfo";
        String info = jedisClient.hget(novelList, field);
        if(info != null && !info.isEmpty()){
            list = JsonUtils.jsonToList(info, Novel.class);
        }else{
            PageHelper.startPage(1, 6);
            list = novelMapper.seletNewNovelInfo();
            jedisClient.hset(novelList, field, JsonUtils.objectToJson(list));
        }
        return list;
    }

    //热门小说
    @Override
    public List<Novel> getHotInfo() {
        List<Novel> list = new ArrayList<>();
        String field = "hotInfo";
        String info = jedisClient.hget(novelList, field);
        if(info != null && !info.isEmpty()){
            list = JsonUtils.jsonToList(info, Novel.class);
        }else{
            PageHelper.startPage(1, 6);
            list = novelMapper.seletHotNovelInfo();
            jedisClient.hset(novelList, field, JsonUtils.objectToJson(list));
        }
        return list;
    }
}
