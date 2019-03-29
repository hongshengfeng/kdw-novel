package com.keduw.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * redis客户端接口实现类
 * @author hsfeng
 */
@Component
public class JedisClientPool implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String set(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.set(key,value);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.get(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Long del(String... key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.del(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.exists(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Long expire(String key, int second) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.expire(key, second);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.ttl(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.incr(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.hset(key, field, value);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.hget(key, field);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.hgetAll(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Long hdel(String key, String... field) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.hdel(key, field);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Long sadd(String key, String... field) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return  jedis.sadd(key, field);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public Long setnx(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.setnx(key, value);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public String getSet(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.getSet(key, value);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
