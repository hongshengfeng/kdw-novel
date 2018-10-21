package com.keduw.service.impl;

import com.keduw.dao.RecordMapper;
import com.keduw.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 章节阅读记录
 * @author hsfeng
 */
@Service("recordService")
public class RecordServiceImpl implements RecordService{

    @Autowired
    private RecordMapper recordMapper;

    //获取阅读记录的章节id
    @Override
    public int getUserRecord(int userId, int novelId) {
        return recordMapper.selectByNovelId(userId, novelId);
    }
}
