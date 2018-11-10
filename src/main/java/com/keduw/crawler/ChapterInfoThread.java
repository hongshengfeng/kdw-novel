package com.keduw.crawler;

import com.keduw.model.Chapter;
import com.keduw.service.ChapterService;
import com.keduw.util.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 将爬取的章节内容保存到本地,
 * 轮询检测队列中的小说内容
 */
public class ChapterInfoThread implements Runnable{

    private BlockingQueue<Chapter> queue = null;  //阻塞队列
    private Logger Log =  (Logger) LoggerFactory.getLogger(ChapterInfoThread.class);
    ChapterInfoThread(BlockingQueue<Chapter> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            int timer = 0;
            if(queue != null && queue.size() > 0){
                Chapter chapter = null;
                try{
                    chapter = queue.poll(100, TimeUnit.MILLISECONDS);
                    ChapterService chapterService = (ChapterService) ApplicationUtil.getBean("chapterService");

                    System.out.println("队列剩余消费个数：" + queue.size());
                }catch (InterruptedException e){
                    Log.error("updateChapterError", e.getMessage());
                }
            }else{
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    Log.error("chapterThreadError", e.getMessage());
                }
                timer ++ ;
            }
            //timer大于3(9秒钟)，则表示队列已经被消费完，退出该循环
            if(timer >= 3){
                break;
            }
        }
    }
}
