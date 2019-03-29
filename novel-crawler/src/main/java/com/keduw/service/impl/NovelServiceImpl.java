package com.keduw.service.impl;

import com.github.pagehelper.PageHelper;
import com.keduw.dao.NovelMapper;
import com.keduw.model.Novel;
import com.keduw.service.NovelService;
import com.keduw.utils.JedisClient;
import com.keduw.utils.JsonUtils;
import com.keduw.utils.Page;
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

    @Override
    public int getNovelSize(int id) {
        return novelMapper.selectSizeById(id);
    }

}
