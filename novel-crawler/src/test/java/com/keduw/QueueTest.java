package com.keduw;

import com.keduw.utils.MsgUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrawlerApplication.class)
public class QueueTest {

    @Autowired
    private MsgUtil msgUtil;

    @Test
    public void info(){
        msgUtil.send("novel", "Hello RabbitMQ");
    }
}
