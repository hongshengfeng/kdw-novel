package com.keduw.crawler;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.model.NovelColl;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.util.ApplicationUtil;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 将爬取的小说内容保存到本地,
 * 轮询检测队列中的小说内容
 */
public class NovelInfoThread implements Runnable{

    BlockingQueue<NovelColl> novelQueue = null;  //阻塞队列
    NovelInfoThread(BlockingQueue<NovelColl> queue){
        this.novelQueue = queue;
    }

    @Override
    public void run() {
        while (true){
            int timer = 0;
            if(novelQueue != null && novelQueue.size() > 0){
                NovelColl novelColl = null;
                try{
                    novelColl = novelQueue.poll(100, TimeUnit.MILLISECONDS);
                    Novel novel = novelColl.getNovel(); //小说
                    List<Chapter> chapterList = novelColl.getChapters(); //章节列表
                    //查询小说是否有更新
                    NovelService novelService = (NovelService)ApplicationUtil.getBean("novelService");
                    ChapterService chapterService = (ChapterService)ApplicationUtil.getBean("chapterService");
                    int result = novelService.isExitOrUpdate(novel); //0-小说不存在，1-存在，章节需要更新，2-无变化
                    if(result == 0){
                        //增加小说和章节信息
                        int inResult = novelService.insertNovel(novel);
                        if(inResult == 1){
                            int novelId = novel.getId();
                            for(Chapter chapter : chapterList){
                                chapter.setnId(novelId);
                            }
                            chapterService.insertChapter(chapterList);
                        }
                    }else if(result == 1){
                        //已存在该小说，只需更新章节信息
                        int novelId = novel.getId();
                        for(Chapter chapter : chapterList){
                            chapter.setnId(novelId);
                        }
                        chapterService.updateChapter(chapterList);
                    }else {
                        continue;
                    }
                    System.out.println("队列剩余消费个数：" + novelQueue.size());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else{
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
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
