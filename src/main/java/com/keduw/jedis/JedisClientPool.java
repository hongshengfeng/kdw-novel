package com.keduw.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis客户端接口实现类
 * @author hsfeng
 */
@Component
public class JedisClientPool implements JedisClient {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Jedis jedis;

    @Override
    public String set(String key, String value) {
        try{
            jedis = jedisPool.getResource();
            return jedis.set(key,value);
        }finally {
            jedis.close();
        }
    }

    @Override
    public String get(String key) {
        try{
            jedis = jedisPool.getResource();
            return  jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    @Override
    public Long del(String... key) {
        try{
            jedis = jedisPool.getResource();
            return  jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    @Override
    public Boolean exists(String key) {
        try{
            jedis = jedisPool.getResource();
            return  jedis.exists(key);
        }finally {
            jedis.close();
        }
    }

    @Override
    public Long expire(String key, int second) {
        try{
            jedis = jedisPool.getResource();
            return  jedis.expire(key, second);
        }finally {
            jedis.close();
        }
    }

    @Override
    public Long ttl(String key) {
        try{
            jedis = jedisPool.getResource();
            return  jedis.ttl(key);
        }finally {
            jedis.close();
        }
    }

    @Override
    public Long incr(String key) {
        try{
            jedis = jedisPool.getResource();
            return  jedis.incr(key);
        }finally {
            jedis.close();
        }
    }

    @Override
    public Long hset(String key, String field, String value) {
        try{
            jedis = jedisPool.getResource();
            return  jedis.hset(key, field, value);
        }finally {
            jedis.close();
        }
    }

    @Override
    public String hget(String key, String field) {
        try{
            jedis = jedisPool.getResource();
            return  jedis.hget(key, field);
        }finally {
            jedis.close();
        }
    }

    @Override
    public Long hdel(String key, String... field) {
        try{
            jedis = jedisPool.getResource();
            return  jedis.hdel(key, field);
        }finally {
            jedis.close();
        }
    }
}
