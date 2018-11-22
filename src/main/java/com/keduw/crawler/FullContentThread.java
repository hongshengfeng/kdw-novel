package com.keduw.crawler;

import com.keduw.model.Chapter;
import com.keduw.service.ChapterService;
import com.keduw.util.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

//完整章节内容，更新到数据库的线程
public class FullContentThread implements Runnable{

    private Logger Log =  (Logger) LoggerFactory.getLogger(FullContentThread.class);
    private BlockingQueue<Chapter> updateQueue = null;  //阻塞队列
    FullContentThread(BlockingQueue<Chapter> updateQueue){
        this.updateQueue = updateQueue;
    }
    @Override
    public void run() {
        int timer = 0;
        while (timer < 3){
            try {
                if (updateQueue != null && updateQueue.size() > 0) {
                    timer = 0;
                    ChapterService chapterService = (ChapterService) ApplicationUtil.getBean("chapterService");
                    Chapter chapter = updateQueue.poll(1000, TimeUnit.MILLISECONDS);
                    chapterService.updateChapterContent(chapter);
                    System.out.println("待更新：" + updateQueue.size());
                } else {
                    Thread.sleep(30000);
                    timer++;
                }
            }catch (Exception e){
                Log.error("novelContentError", e.getMessage());
            }
        }
        System.out.println("end");
    }
}
