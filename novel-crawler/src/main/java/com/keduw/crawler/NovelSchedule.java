package com.keduw.crawler;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.utils.JsonUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 小说爬虫，包含小说内容爬取，章节爬取，章节内容更新日常检查
 * @author hsfeng
 * @date 2019-04-07
 */
@Component
@PropertySource("classpath:application.properties")
public class NovelSchedule {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private NovelService novelServicel;
    @Value("${mq.config.exchange}")
    private String novelExchange;
    @Value("${mq.config.routing.novel.key}")
    private String novelRouting;
    @Value("${mq.config.routing.chapter.key}")
    private String chapterRouting;
    @Value("${mq.config.routing.chapter.update.key}")
    private String chapterUpdateRouting;

    //每周六凌晨0点启动爬取小说
    @Scheduled(cron = "0 0 13 ? * SAT")
    public void novelCollect() throws Exception{
        NovelCrawler crawl = new NovelCrawler("crawl", true, amqpTemplate, novelExchange, novelRouting);
        crawl.start(5);
    }

    //每周六下午13点爬取章节内容
    @Scheduled(cron = "0 0 13 ? * SAT")
    public void chapterCollect() throws Exception{
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

    //每天0点检查连载中小说的章节更新情况
    @Scheduled(cron = "0 0 0 * * ?")
    public void infoCheck() throws Exception{
        //小说总数
        int counts = novelServicel.getNovelCount();
        List<Novel> novelList = null;
        for(int i = 1; i <= counts; i++){
            novelList = novelServicel.getNovelList(i, SIZE);
            for(Novel novel : novelList){
                amqpTemplate.convertAndSend(novelExchange, chapterUpdateRouting, JsonUtils.objectToJson(novel));
            }
        }
    }

    private static final int SIZE = 100;
}
