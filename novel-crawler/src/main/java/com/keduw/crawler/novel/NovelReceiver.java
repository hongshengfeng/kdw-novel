package com.keduw.crawler.novel;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.model.NovelColl;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 从消息队列中获取小说爬取的章节和基本信息
 * 保存到数据库
 * @author hsfeng
 * @date 2019-04-07
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.novel}", autoDelete = "false"),
                exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.routing.novel.key}"
        )
)
public class NovelReceiver {

    @Autowired
    private NovelService novelService;
    @Autowired
    private ChapterService chapterService;

    @RabbitHandler
    public void novelInfo(String msg){
        try{
            NovelColl novelColl = JsonUtils.jsonToPojo(msg, NovelColl.class);
            Novel novel = novelColl.getNovel(); //小说
            List<Chapter> chapterList = novelColl.getChapters(); //章节列表
            //查询小说是否有更新 0-小说不存在，1-存在，章节需要更新，2-无变化
            int result = novelService.isExitOrUpdate(novel);
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
        }catch (Exception e){
            logger.error("crawler err;", e.getMessage());
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(NovelReceiver.class);
}
