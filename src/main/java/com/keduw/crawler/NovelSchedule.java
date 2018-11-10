package com.keduw.crawler;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.model.NovelColl;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.util.ApplicationUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//小说爬虫
@Component
public class NovelSchedule {

    private volatile BlockingQueue<NovelColl> novelQueue = new LinkedBlockingQueue<NovelColl>(10000 * 10);

    //每月10凌晨3点启动爬取小说
    @Scheduled(cron = "0 38 11 * * ?")
    public void novelCollect() throws Exception{
        if(true){
            ReentrantLock lock = new ReentrantLock();
            NovelCrawler crawl = new NovelCrawler("crawl",true, novelQueue, lock);
            NovelInfoThread saveInfo = new NovelInfoThread(novelQueue);
            Thread thread = new Thread(saveInfo);
            thread.start();
            crawl.start(5);
        }
    }

    //每天3点检查连载中小说的章节更新情况
    @Scheduled(cron = "0 0 3 * * ?")
    public void infoCheck() throws Exception{
        if(isOpen){
            //小说总数
            NovelService novelService = (NovelService) ApplicationUtil.getBean("novelService");
            int counts = novelService.getNovelCount();
            int checkTimes = counts / 100;
            checkTimes = counts % 100 == 0 ? checkTimes : checkTimes + 1;
            int collStart = 1;
            BlockingQueue<Chapter> queue = new LinkedBlockingQueue<Chapter>(10000 * 10);
            // 分批次检查小说的章节
            /*for(int i = 0; i < checkTimes; i++) {
                while (collStart <= checkTimes) {
                    List<Novel> novelList = novelService.getNovelList(collStart, 50);
                    for(Novel novel : novelList){
                        if(novel.getStatus().equals("连载中")){
                            CheckCrawler crawler = new CheckCrawler("crawl",true, novel, queue);
                            crawler.start(1);
                        }
                    }
                    collStart ++;
                }
            }*/
        }
    }

    //每月1号凌晨3点爬取章节内容
    @Scheduled(cron = "0 0 3 1 * ?")
    public void infoCollect() throws Exception{
        if(isOpen){
            //获取总章节数
            ChapterService chapterService = (ChapterService) ApplicationUtil.getBean("chapterService");
            int counts = chapterService.getInfoCounts();
            int collTimes = counts / 100;
            collTimes = counts % 100 == 0 ? collTimes : collTimes + 1;
            int collStart = 1;
            for(int i = 0; i < collTimes; i++){
                while(collStart <= collTimes){
                    List<Chapter> chapterList = chapterService.getChapterList(collStart, 5);
                    // 阻塞队列用于存储一个章节内有多个页面的章节
                    BlockingQueue<Chapter> chapterQueue = new LinkedBlockingQueue<Chapter>(10000 * 10);
                    for(Chapter chapter : chapterList){
                        ChapterCrawler crawler = new ChapterCrawler("crawl", true, chapter, chapterQueue);
                        crawler.start(1);
                    }
                    // 爬取下一页
                    while (chapterQueue.size() > 0){
                        Chapter chapter = chapterQueue.poll(1000, TimeUnit.MILLISECONDS);
                        ChapterCrawler crawler = new ChapterCrawler("crawl", true, chapter, chapterQueue);
                        crawler.start(1);
                    }
                    // 更新章节内容
                    chapterService.updateChapterContent(chapterList);
                }
                collStart ++;
            }
        }
    }

    private boolean isOpen = true; //启动开关，日常关闭
}
