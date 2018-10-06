package com.keduw.crawler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 小说爬虫
 */
@Component
public class NovelSchedule {

    // 每个月的6号15：30启动小说爬虫，会爬取所有小说
    @Scheduled(cron = "0 31 15 6 * ? ")
    public void novelCollect() throws Exception{
        NovelCrawler crawl = new NovelCrawler("crawl",true);
        NovelInfoThread saveInfo = new NovelInfoThread(crawl.novelQueue);
        Thread thread = new Thread(saveInfo);
        thread.start();
        crawl.start(5);
    }
}
