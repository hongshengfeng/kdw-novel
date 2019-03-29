package com.keduw.service.impl;

import com.keduw.dao.IpinfoMapper;
import com.keduw.model.Ipinfo;
import com.keduw.service.SeoService;
import com.keduw.utils.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("seoService")
public class SeoServiceImpl implements SeoService {

    @Autowired
    private IpinfoMapper ipinfoMapper;

    //获取最近7天的访客记录
    @Override
    public List<Ipinfo> infoList() {
        Date date = new Date();
        List<Ipinfo> list = ipinfoMapper.selectInfoByList(date);
        for(Ipinfo info : list){
            info.setsTime(DateFormat.format(info.getTime()));
        }
        return list;
    }

    //记录每天ip访问数据
    @Override
    public int insertInfo(Ipinfo ipinfo) {
        Date curr = ipinfo.getTime();
        Ipinfo info = ipinfoMapper.selectInfoByTime(curr);
        int rt = info == null ? ipinfoMapper.insertInfo(ipinfo) : 0;
        return rt;
    }
}
