package com.keduw.dao;

import org.apache.ibatis.annotations.Param;

public interface RecordMapper {

    //根据用户id和小说id查询阅读章节id
    int selectByNovelId(@Param("userId") int userId, @Param("novelId") int novelId);
}