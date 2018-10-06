package com.keduw.crawler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 小说爬虫
 */
@Component
public class NovelSchedule {

    // 每天凌晨一点启动小说爬虫
    @Scheduled(cron = "0 38 8 * * ? ")
    public void novelCollect() throws Exception{
        NovelCrawler crawl = new NovelCrawler("crawl",true);
        SaveInfoThread saveInfo = new SaveInfoThread(crawl.novelQueue);
        Thread thread = new Thread(saveInfo);
        thread.start();
        crawl.start(5);
    }
}
