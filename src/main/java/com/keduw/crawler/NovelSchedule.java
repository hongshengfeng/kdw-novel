package com.keduw.crawler;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.util.ApplicationUtil;
import com.keduw.util.JsonUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 小说爬虫
 */
@Component
public class NovelSchedule {

    //每周六的0点启动小说爬虫，爬取是否有新小说
    @Scheduled(cron = "0 0 0 * * SAT")
    public void novelCollect() throws Exception{
        NovelCrawler crawl = new NovelCrawler("crawl",true);
        crawl.start(5);
        NovelInfoThread saveInfo = new NovelInfoThread(crawl.novelQueue);
        Thread thread = new Thread(saveInfo);
        thread.start();
    }

    //每天0点30分检查连载中小说的章节更新情况
    @Scheduled(cron = "0 30 0 * * ? ")
    public void infoCheck() throws Exception{
        //小说总数
        NovelService novelService = (NovelService) ApplicationUtil.getBean("novelService");
        int counts = novelService.getNovelCount();
        int times = counts / 100;
        times = counts % 100 == 0 ? times : times + 1;
        // 分批次检查小说的章节
        List<Novel> novelList = new ArrayList<>();
        for(int i = 1; i <= times; i++){
            novelList = novelService.getNovelList(i, 100);
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

    //每天1点爬取章节内容
    @Scheduled(cron = "0 51 21 * * ? ")
    public void infoCollect() throws Exception{
        //获取总章节数
        ChapterService chapterService = (ChapterService) ApplicationUtil.getBean("chapterService");
        int counts = chapterService.getInfoCounts();
        int times = counts / 100;
        times = counts % 100 == 0 ? times : times + 1;
        List<Chapter> chapterList = new ArrayList<>();
        for(int i = 1; i <= times; i++){
            chapterList = chapterService.getChapterList(i, 100);
            // 阻塞队列用于存储一个章节内有多个页面的章节
            BlockingQueue<Chapter> chapterQueue = new LinkedBlockingQueue<Chapter>(10000 * 10);
            for(Chapter chapter : chapterList){
                ChapterCrawler crawler = new ChapterCrawler("crawl", true, chapter, chapterQueue);
                crawler.start(1);
            }
            // 爬取下一页
            while (chapterQueue.size() > 0){
                Chapter chapter = chapterQueue.poll(100, TimeUnit.MILLISECONDS);
                ChapterCrawler crawler = new ChapterCrawler("crawl", true, chapter, chapterQueue);
                crawler.start(1);
                System.out.println("章节内容：" + JsonUtils.objectToJson(chapter));
            }
            // 更新章节内容
            chapterService.updateChapter(chapterList);
            System.out.println("已更新批次：" + i);
        }
        System.out.println("更新结束");
    }

}
