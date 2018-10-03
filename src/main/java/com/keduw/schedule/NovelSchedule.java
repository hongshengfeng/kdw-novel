package com.keduw.schedule;


import com.keduw.crawler.ChapterCrawler;
import com.keduw.crawler.NovelCrawler;
import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.util.ContentUtil;
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
        List<Novel> novelList = novelService.getAllNovelInfo();
        for (Novel novel:novelList) {
            NovelCrawler novelCrawler = new NovelCrawler("update/novel"+novel.getNovelid(),false);
            novelCrawler.addSeed(novel.getNovelurl());
            try {
                novelCrawler.start(1);
                while (novelCrawler.isResumable()){

                }

                Novel tmp= novelCrawler.getNovelsList().get(0);
                if(tmp.getChaptersize() > novel.getChaptersize()){
                //TODO 查询章节号并更新(未测试)
                List<Chapter> chapters = ContentUtil.containChapter(novelCrawler.getChapterList(),novel.getChaptersize());
                    for (Chapter c : chapters) {
                        c.setNovelid(novel.getNovelid());
                        ChapterCrawler chapterCrawler = new ChapterCrawler("update/time/chapter"+novel.getNovelid(),false,c);
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
