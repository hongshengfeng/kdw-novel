package com.keduw.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue addNovelQueue(){
        return new Queue("novel");
    }

    @Bean
    public Queue addChapterQueue(){
        return new Queue("chapter");
    }


}
