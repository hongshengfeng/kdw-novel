package com.keduw.service;


import com.keduw.model.Ipinfo;

import java.util.List;

public interface SeoService {

    //获取最近7天的访客记录
    List<Ipinfo> infoList();

    int insertInfo(Ipinfo ipinfo);
}
