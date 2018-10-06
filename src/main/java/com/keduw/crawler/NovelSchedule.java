package com.keduw.crawler;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.util.ApplicationUtil;
import com.keduw.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 小说爬虫
 */
@Component
public class NovelSchedule {

    //每周六的1点启动小说爬虫，爬取是否有新小说
    @Scheduled(cron = "0 0 1 * * SAT")
    public void novelCollect() throws Exception{
        NovelCrawler crawl = new NovelCrawler("crawl",true);
        crawl.start(5);
        NovelInfoThread saveInfo = new NovelInfoThread(crawl.novelQueue);
        Thread thread = new Thread(saveInfo);
        thread.start();
    }

    //每天1点30分检查连载中小说的章节更新情况
    @Scheduled(cron = "0 30 1 * * ? ")
    public void infoCheck() throws Exception{
        //小说总数
        NovelService novelService = (NovelService) ApplicationUtil.getBean("novelService");
        int counts = novelService.getNovelCount();
        int times = counts / 1000;
        times = counts % 1000 == 0 ? times : times + 1;
        // 分批次检查小说的章节
        List<Novel> novelList = new ArrayList<>();
        for(int i = 1; i <= times; i++){
            novelList = novelService.getNovelList(i, 1000);
            for(Novel novel : novelList){
                if(novel.getStatus().equals("连载中")){
                    CheckCrawler crawler = new CheckCrawler("crawl",true, novel);
                    crawler.start(2);
                    while (crawler.isResumable()){
                    }
                }
            }
        }
    }

    //每天2点30分爬取章节内容
    @Scheduled(cron = "0 25 22 * * ? ")
    public void infoCollect() throws Exception{
        //获取总章节数
        ChapterService chapterService = (ChapterService) ApplicationUtil.getBean("chapterService");
        int counts = chapterService.getInfoCounts();
        int times = counts / 1000;
        times = counts % 1000 == 0 ? times : times + 1;
        System.out.println("总批次：" + times);
        List<Chapter> chapterList = new ArrayList<>();
        for(int i = 1; i <= times; i++){
            chapterList = chapterService.getChapterList(i, 1000);
            for(Chapter chapter : chapterList){
                ChapterCrawler crawler = new ChapterCrawler("crawl", true, chapter);
                crawler.start(2);
                while (crawler.isResumable()){
                }
            }
            System.out.println("已更新批次：" + i);
        }
        System.out.println("更新结束");
    }

}
