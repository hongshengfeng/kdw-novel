package com.keduw.crawler;

import com.keduw.model.Novel;
import com.keduw.service.NovelService;
import com.keduw.util.ApplicationUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 小说爬虫
 */
@Component
public class NovelSchedule {

    //每周六的1：30启动小说爬虫，爬取是否有新小说
    @Scheduled(cron = "0 30 1 * * SAT")
    public void novelCollect() throws Exception{
        NovelCrawler crawl = new NovelCrawler("crawl",true);
        crawl.start(5);
        NovelInfoThread saveInfo = new NovelInfoThread(crawl.novelQueue);
        Thread thread = new Thread(saveInfo);
        thread.start();
    }

    //每天3点检查连载中小说的章节更新情况
    @Scheduled(cron = "0 22 18 * * ? ")
    public void infoCheck() throws Exception{
        //小说总数
        NovelService novelService = (NovelService) ApplicationUtil.getBean("novelService");
        int counts = novelService.getNovelCount();
        int times = counts / 1000;
        times = counts % 10 == 0 ? times : times + 1;
        // 分批次检查小说的章节
        List<Novel> novelList = new ArrayList<>();
        for(int i = 1; i <= times; i++){
            novelList = novelService.getNovelList(i, 1000);
            for(Novel novel : novelList){
                if(novel.getStatus().equals("连载中")){
                    CheckCrawler check = new CheckCrawler("crawl",true, novel);
                    check.start(2);
                }
            }
        }
    }

}
