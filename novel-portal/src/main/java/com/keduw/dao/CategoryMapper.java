package com.keduw.dao;

import com.keduw.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 获取类别信息接口
 * @author hsfeng
 */
@Mapper
public interface CategoryMapper {

    // 获取所有小说类别
    List<Category> selectInfoList();
}