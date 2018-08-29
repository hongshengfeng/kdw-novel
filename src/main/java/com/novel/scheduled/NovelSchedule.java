package com.novel.scheduled;

import com.novel.crawler.ChapterCrawler;
import com.novel.crawler.NovelCrawler;
import com.novel.model.Chapter;
import com.novel.model.Novel;
import com.novel.service.ChapterService;
import com.novel.service.NovelService;
import com.novel.utils.ChapterUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NovelSchedule {
    @Resource
    private NovelService novelService;
    @Resource
    private ChapterService chapterService;

    //@Scheduled(cron = "0/2 * * * * *")
    public void timer() {
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
    //@Scheduled(cron = "0/2 * * * * *")
    public void  novel() {

        List<Novel> novelList = novelService.findAllNovel();
        for (Novel novel:novelList
             ) {
            NovelCrawler novelCrawler = new NovelCrawler("update/novel"+novel.getNovelId(),false);
            novelCrawler.addSeed(novel.getNovelUrl());
            try {
                novelCrawler.start(1);
                while (novelCrawler.isResumable()){

                }

                Novel tmp= novelCrawler.getNovelsList().get(0);
                if(tmp.getChapterSize()>novel.getChapterSize()){
                //TODO 查询章节号并更新(未测试)
                List<Chapter> chapters = ChapterUtil.containChapter(novelCrawler.getChapterList(),novel.getChapterSize());
                    for (Chapter c :
                            chapters) {
                        c.setNovelId(novel.getNovelId());
                        ChapterCrawler chapterCrawler = new ChapterCrawler("update/time/chapter"+novel.getNovelId(),false,c);
                        chapterCrawler.start(1);

                        while (chapterCrawler.isResumable()){
                        }
                         Chapter result = chapterCrawler.getChapter();
                         chapterService.updateChapter(result);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }
}
