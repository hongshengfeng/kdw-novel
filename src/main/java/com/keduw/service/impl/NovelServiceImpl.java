package com.keduw.service.impl;

import com.github.pagehelper.PageHelper;
import com.keduw.dao.NovelMapper;
import com.keduw.jedis.JedisClient;
import com.keduw.model.Novel;
import com.keduw.service.NovelService;
import com.keduw.util.JsonUtils;
import com.keduw.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        fields.append(novelList);
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
        if(category.length > 0){
            list = novelMapper.selectNovelByCategory(category[0], curr, size);
        }else{
            list = novelMapper.selectNovel(curr, size);
        }
        if(list != null && list.size() > 0){
            jedisClient.hset(novelList, fields.toString(), JsonUtils.objectToJson(list));
        }else {
            // 获取为空则放回第一页数据
            curr = 1;
            if(category.length > 0){
                list = novelMapper.selectNovelByCategory(category[0], curr, size);
            }else{
                list = novelMapper.selectNovel(curr, size);
            }
        }
        return list;
    }

    //插入小说信息
    @Override
    public int insertNovel(Novel novel) {
        int novelId = 0;
        if(novel != null){
            novelId = novelMapper.insertNovel(novel);
            if(novelId > 0){
                //清除原有的redis缓存
                jedisClient.del(novelList);
            }
        }
        return novelId;
    }

    //通过关键字查询小说
    @Override
    public Page<Novel> getNovelByName(String wd, int start) {
        Page<Novel> result = new Page<>();
        int counts = novelMapper.selectNovelCountByName(wd);
        int size = result.getRows();
        int totalPage = counts % size == 0 ? counts/size : counts/size + 1;
        start = start > totalPage ? totalPage * size : start * size;
        List<Novel> list = novelMapper.selectNovelByName(wd, start, size);
        result.setTotalRecord(counts);
        result.setTotalPage(totalPage);
        result.setKeyWord(wd);
        result.setStart(start);
        result.setList(list);
        return result;
    }

    //关键字查询小说，不分页
    @Override
    public List<Novel> getNovelByName(String wd) {
        List<Novel> list = novelMapper.selectInfoListByName(wd);
        return list;
    }

    //通过novelId查找小说
    @Override
    public Novel getNovelById(int novelId) {
        Novel novel = new Novel();
        String fields = "novel" + novelId;
        String info = jedisClient.hget(novelInfo, fields);
        if(info != null && !info.isEmpty()){
            novel = JsonUtils.jsonToPojo(info, Novel.class);
        }else{
            novel = novelMapper.selectNovelById(novelId);
            if(novel != null) {
                jedisClient.hset(novelInfo, fields, JsonUtils.objectToJson(novel));
            }
        }
        return novel;
    }

    //最新小说
    @Override
    public List<Novel> getNewInfo() {
        List<Novel> list = new ArrayList<>();
        Random random = new Random();
        int index = random.nextInt(10);
        String field = "newInfo" + index;
        String info = jedisClient.hget(novelList, field);
        if(info != null && !info.isEmpty()){
            list = JsonUtils.jsonToList(info, Novel.class);
        }else{
            PageHelper.startPage(index, 6);
            list = novelMapper.seletNewNovelInfo();
            if(list != null && list.size() > 0){
                jedisClient.hset(novelList, field, JsonUtils.objectToJson(list));
            }
        }
        return list;
    }

    //热门小说
    @Override
    public List<Novel> getHotInfo() {
        List<Novel> list = new ArrayList<>();
        Random random = new Random();
        int index = random.nextInt(10);
        String field = "hotInfo" + index;
        String info = jedisClient.hget(novelList, field);
        if(info != null && !info.isEmpty()){
            list = JsonUtils.jsonToList(info, Novel.class);
        }else{
            PageHelper.startPage(index, 6);
            list = novelMapper.seletHotNovelInfo();
            if(list != null && list.size() > 0){
                jedisClient.hset(novelList, field, JsonUtils.objectToJson(list));
            }
        }
        return list;
    }

    //判断小说是否存在或更新
    @Override
    public int isExitOrUpdate(Novel novel) {
        String name = novel.getName();
        String author = novel.getAuthor();
        int size = novel.getSize();
        int result = 0; //0-小说不存在，1-存在，章节需要更新，2-无变化
        if(name != null && !name.isEmpty()){
            Novel info = novelMapper.selectInfoByName(name, author);
            if(info != null){
                novel.setId(info.getId());
                result = info.getSize() == size ? 2 : 1;
            }
        }
        return result;
    }

    //获取小说总数
    @Override
    public int getNovelCount() {
        return novelMapper.selectInfoCount();
    }

    //获取某个类别的小说总数
    @Override
    public int getNovelCountByCategory(int category) {
        return novelMapper.selectInfoCountByCategory(category);
    }

    @Override
    public int getNovelSize(int id) {
        return novelMapper.selectSizeById(id);
    }

}
