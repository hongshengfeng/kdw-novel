package com.keduw.utils;

import java.util.Map;

/**
 * redis客户端接口
 * @author hsfeng
 */
public interface JedisClient {
    //String数据类型存储数据
    String set(String key, String value);

    //根据key获取String类型数据
    String get(String key);

    //删除String类型数据
    Long del(String... key);

    //判断ke是否存在
    Boolean exists(String key);

    //修改key的生存时间
    Long expire(String key, int second);

    //放回给定key的剩余生存时间
    Long ttl(String key);

    //将key中存储的数据值增1
    Long incr(String key);

    //hash数据类型存储数据
    Long hset(String key, String field, String value);

    //根据key和field获取hash数据类型
    String hget(String key, String field);

    //获取hash数据类型的所有fields和value
    Map<String, String> hgetAll(String key);

    //删除hash数据类型
    Long hdel(String key, String... field);

    //添加set数据类型
    Long sadd(String key, String... field);

    //添加数据，存在返回0，不存在添加返回1
    Long setnx(String key, String value);

    //更新数据，返回旧的数据
    String getSet(String key, String value);
}
