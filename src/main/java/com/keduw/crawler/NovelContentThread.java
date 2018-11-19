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

    private Logger Log =  (Logger) LoggerFactory.getLogger(NovelInfoThread.class);
    private BlockingQueue<Chapter> updateQueue = null;  //阻塞队列
    NovelContentThread(BlockingQueue<Chapter> updateQueue){
        this.updateQueue = updateQueue;
    }
    @Override
    public void run() {
        while (true){
            int timer = 0;
            if(updateQueue != null && updateQueue.size() > 0){
                ChapterService chapterService = (ChapterService) ApplicationUtil.getBean("chapterService");
                try{
                    Chapter chapter = updateQueue.poll(1000, TimeUnit.MILLISECONDS);
                    chapterService.updateChapterContent(chapter);
                }catch (InterruptedException e){
                    Log.error("update novel content error", e.getMessage());
                }
                System.out.println("队列剩余消费个数" + updateQueue.size());
            }else{
                try{
                    Thread.sleep(30000);
                }catch (InterruptedException e){
                    Log.error("novelThreadError", e.getMessage());
                }
                timer ++ ;
            }
            //timer大于3(60秒钟)，则表示队列已经被消费完，退出该循环
            if(timer >= 3){
                break;
            }
        }
    }
}
