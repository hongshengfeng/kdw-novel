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
    @Scheduled(cron = "0 39 15 * * ?")
    public void novelCollect() throws Exception{
        if(isOpen){
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
            BlockingQueue<List<Chapter>> queue = new LinkedBlockingQueue<List<Chapter>>(10000 * 10);
            // 分批次检查小说的章节
            for(int i = 0; i < checkTimes; i++) {
                while (collStart <= checkTimes) {
                    List<Novel> novelList = novelService.getNovelList(collStart, 100);
                    for(Novel novel : novelList){
                        if(novel.getStatus().equals("连载中")){
                            CheckCrawler crawler = new CheckCrawler("crawl",true, novel, queue);
                            crawler.start(1);
                        }
                    }
                    collStart ++;
                }
            }

        }
    }

    //每月1号凌晨3点爬取章节内容
    @Scheduled(cron = "0 57 23 * * ?")
    public void infoCollect() throws Exception{
        if(true){
            //获取总章节数
            ChapterService chapterService = (ChapterService) ApplicationUtil.getBean("chapterService");
            int counts = chapterService.getInfoCounts();
            int collTimes = counts / 100;
            collTimes = counts % 100 == 0 ? collTimes : collTimes + 1;
            BlockingQueue<Chapter> chapterQueue = new LinkedBlockingQueue<Chapter>(10000 * 10); //存取有下一页的内容
            BlockingQueue<Chapter> updateQueue = new LinkedBlockingQueue<Chapter>(10000 * 10); //存取更新到数据库的内容
            for(int i = 0; i < collTimes; i++){
                List<Chapter> chapterList = chapterService.getChapterList(i * 30, 30);
                // 阻塞队列用于存储一个章节内有多个页面的章节
                for(Chapter chapter : chapterList){
                    ChapterCrawler crawler = new ChapterCrawler("crawl", true, chapter, chapterQueue, updateQueue);
                    crawler.start(1);
                }
                if(i == 0){
                    NextPageThread nextPage = new NextPageThread(chapterQueue, updateQueue);
                    NovelContentThread novelContent = new NovelContentThread(updateQueue);
                    Thread pageThread = new Thread(nextPage);
                    Thread contentThread = new Thread(novelContent);
                    pageThread.start();
                    contentThread.start();
                }
            }
        }
    }

    private boolean isOpen = false; //启动开关，日常关闭
}
