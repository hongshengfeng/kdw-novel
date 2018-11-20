package com.keduw.crawler;

import com.keduw.model.Chapter;
import com.keduw.service.ChapterService;
import com.keduw.util.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

//章节内容
public class NovelContentThread implements Runnable{

    private Logger Log =  (Logger) LoggerFactory.getLogger(NovelContentThread.class);
    private BlockingQueue<Chapter> updateQueue = null;  //阻塞队列
    NovelContentThread(BlockingQueue<Chapter> updateQueue){
        this.updateQueue = updateQueue;
    }
    @Override
    public void run() {
        while (true){
            int timer = 0;
            try {
                if (updateQueue != null && updateQueue.size() > 0) {
                    ChapterService chapterService = (ChapterService) ApplicationUtil.getBean("chapterService");
                    Chapter chapter = updateQueue.poll(1000, TimeUnit.MILLISECONDS);
                    chapterService.updateChapterContent(chapter);
                    System.out.println("待消费：" + updateQueue.size());
                } else {
                    Thread.sleep(60000);
                    timer++;
                }
                //timer大于3(180秒钟)，则表示队列已经被消费完，退出该循环
                if(timer >= 3){
                    break;
                }
            }catch (Exception e){
                Log.error("novelContentError", e.getMessage());
            }
        }
        System.out.println("end");
    }
}
