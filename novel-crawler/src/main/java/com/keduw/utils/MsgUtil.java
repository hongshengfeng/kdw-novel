package com.keduw.utils;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgUtil {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 消息发送
     * @param name 队列名称
     * @param msg 发送内容
     */
    public void send(String name, String msg){
        amqpTemplate.convertAndSend(name, msg);
    }

    @RabbitListener(queues = {"novel", "chapter"})
    public void process(String msg){
        System.out.println("获取消息：" + msg);
    }

}
