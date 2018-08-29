package com.novel;

import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NovelApplication.class)
@Component
public class redisTest {



        @Autowired
        RedisConnectionFactory factory;

        @Test
        public void testRedis(){
            //得到一个连接
            RedisConnection conn = factory.getConnection();
            conn.set("hello".getBytes(), "world".getBytes());
            System.out.println(new String(conn.get("hello".getBytes())));
        }


}
