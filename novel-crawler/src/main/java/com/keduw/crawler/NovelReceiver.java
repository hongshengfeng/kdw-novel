package com.keduw.crawler;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 小说信息消费者
 */

@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.novel", autoDelete = "false"),
                exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.routing.key}"
        )
)
public class NovelReceiver {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitHandler
    public void process(String msg){
        System.out.println("获取消息：" + msg);
    }

}
