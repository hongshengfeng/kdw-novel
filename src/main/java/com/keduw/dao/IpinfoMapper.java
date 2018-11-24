package com.keduw.dao;

import com.keduw.model.Ipinfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface IpinfoMapper {

    List<Ipinfo> selectInfoByList(Date date);

    int insertInfo(Ipinfo ipinfo);

    Ipinfo selectInfoByTime(Date curr);
}