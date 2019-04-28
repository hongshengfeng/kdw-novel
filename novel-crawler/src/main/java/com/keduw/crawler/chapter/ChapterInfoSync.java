package com.keduw.crawler.chapter;

import com.keduw.model.Chapter;
import com.keduw.service.ChapterService;
import com.keduw.utils.JsonUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 随系统启动
 * 每隔10分钟爬取没有内容的章节
 * @author hsfeng
 */

@Component
public class ChapterInfoSync implements ApplicationRunner{

    @Autowired
    private ChapterService chapterService;
    @Value("${mq.config.exchange}")
    private String novelExchange;
    @Value("${mq.config.routing.chapter.key}")
    private String chapterRouting;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        while (true){
            int counts = chapterService.getInfoCounts();
            List<Chapter> chapterList = null;
            for(int i = 1; i <= counts; i++){
                chapterList = chapterService.getChapterList(i , SIZE);
                for(Chapter chapter : chapterList){
                    amqpTemplate.convertAndSend(novelExchange, chapterRouting, JsonUtils.objectToJson(chapter));
                }
                TimeUnit.MINUTES.sleep(5);
            }
        }
    }

    private static final int SIZE = 200;
}
