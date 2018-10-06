package com.keduw.crawler;

import com.keduw.model.NovelColl;
import com.keduw.util.JsonUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 将爬取的小说内容保存到本地,
 * 轮询检测队列中的小说内容
 */
public class SaveInfoThread implements Runnable{

    private BlockingQueue<NovelColl> novelQueue = null;  //阻塞队列

    SaveInfoThread(BlockingQueue<NovelColl> queue){
        this.novelQueue = queue;
    }

    @Override
    public void run() {
        while (true){
            if(novelQueue != null && novelQueue.size() > 0){
                try{
                    NovelColl novelColl = novelQueue.poll(100, TimeUnit.MILLISECONDS);
                    System.out.println("小说：" + JsonUtils.objectToJson(novelColl.getNovel()));
                    System.out.println("章节：" + JsonUtils.objectToJson(novelColl.getChapters()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
