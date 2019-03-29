package com.keduw.service.impl;

import com.keduw.dao.CategoryMapper;
import com.keduw.model.Category;
import com.keduw.service.CategoryService;
import com.keduw.utils.JedisClient;
import com.keduw.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 小说类别实现类
 * @author hsfeng
 */
@Service("categoryService")
@PropertySource("classpath:cache.properties")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("catagory_info")
    private String keys;

    //查询章节列表
    @Override
    public List<Category> getInfoList() {
        List<Category> list = new ArrayList<>();
        String info = jedisClient.get(keys);
        if(info != null && !info.isEmpty()){
            list = JsonUtils.jsonToList(info, Category.class);
        }else{
            list = categoryMapper.selectInfoList();
            jedisClient.set(keys, JsonUtils.objectToJson(list));
        }
        return list;
    }
}
