package com.novel.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class NovelSchedule {

    //@Scheduled(cron = "0/2 * * * * *")
    public void timer() {
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public void  novel(){

    }
}
