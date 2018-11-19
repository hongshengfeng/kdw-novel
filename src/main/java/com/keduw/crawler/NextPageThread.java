package com.keduw.crawler;

import com.keduw.model.Chapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

//爬取下一页的线程
public class NextPageThread implements Runnable{

    private Logger Log =  (Logger) LoggerFactory.getLogger(NextPageThread.class);
    private BlockingQueue<Chapter> chapterQueue = null;  //待爬取下一页的队列
    private BlockingQueue<Chapter> updateQueue = null;  //已爬取待插入库里的数据
    NextPageThread(BlockingQueue<Chapter> chapterQueue, BlockingQueue<Chapter> updateQueue){
        this.chapterQueue = chapterQueue;
        this.updateQueue = updateQueue;
    }

    @Override
    public void run() {
        while (true) {
            int timer = 0;
            try {
                if (updateQueue != null && updateQueue.size() > 0) {
                    Chapter chapter = chapterQueue.poll(1000, TimeUnit.MILLISECONDS);
                    ChapterCrawler crawler = new ChapterCrawler("crawl", true, chapter, chapterQueue, updateQueue);
                    crawler.start(1);
                    System.out.println("待爬取下一页的数据：" + chapterQueue.size());
                } else {
                    Thread.sleep(60000);
                    timer++;
                }
            }catch (Exception e) {
                Log.error("novelThreadError", e.getMessage());
            }
            //timer大于3(180秒钟)，则表示队列已经被消费完，退出该循环
            if (timer >= 3) {
                break;
            }
        }
    }
}
