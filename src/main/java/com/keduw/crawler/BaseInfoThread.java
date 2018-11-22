package com.keduw.crawler;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.model.NovelColl;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.util.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

//爬取的小说基本信息保存到本地,
public class BaseInfoThread implements Runnable{

    private Logger Log =  (Logger) LoggerFactory.getLogger(BaseInfoThread.class);
    private BlockingQueue<NovelColl> queue = null;  //阻塞队列
    BaseInfoThread(BlockingQueue<NovelColl> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        int timer = 0;
        while (timer < 3){
            if(queue != null && queue.size() > 0){
                NovelColl novelColl = null;
                try{
                    novelColl = queue.poll(100, TimeUnit.MILLISECONDS);
                }catch (InterruptedException e){
                    Log.error("novelUpdateError", e.getMessage());
                }
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
                }
                System.out.println("队列剩余消费个数：" + queue.size());
            }else{
                try{
                    Thread.sleep(60000);
                }catch (InterruptedException e){
                    Log.error("novelThreadError", e.getMessage());
                }
                timer ++ ;
            }
        }
    }
}
