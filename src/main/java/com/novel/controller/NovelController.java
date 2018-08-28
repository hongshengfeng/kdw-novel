package com.novel.controller;

import com.novel.crawler.ChapterCrawler;
import com.novel.crawler.NovelCrawler;
import com.novel.crawler.UrlCrawler;
import com.novel.enumUrl.EnumUrl;
import com.novel.model.Chapter;
import com.novel.model.Novel;
import com.novel.service.serviceImpl.ChapterServiceImpl;
import com.novel.service.serviceImpl.NovelServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.controller
 * @ClassName: TestController
 * @Description: java类作用描述:先爬去同一类别的小说URL，然后根据小说URL做种子，爬去
 *                  详细信息以及章节列表，然后把详细信息存入数据库，根据章节列表以及
 *                  小说ID爬取主要内容
 * @Author: 林浩东
 * @CreateDate: 2018/8/11/011 16:12
 * @UpdateUser: 林浩东
 * @UpdateDate: 2018/8/11/011 16:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@RestController
@RequestMapping("/Novel")
public class NovelController {
    @Resource
    private NovelServiceImpl novelServiceImpl;
    @Resource
    private ChapterServiceImpl chapterServiceImpl;

    /*爬小说信息以及章节信息*/
    @RequestMapping("/NovelInfo")
    public String NovelInfo(int categoryId){
            UrlCrawler urlCrawler = new UrlCrawler("urlCrawler"+categoryId,true);
            EnumUrl e =EnumUrl.getEnumUrl(categoryId);
            if(e != null){
                urlCrawler.addSeed(e.getUrl());
                urlCrawler.addRegex(e.getRegex());
                urlCrawler.setRegex(e.getRegex());
            }


        try {
            //深度暂时设为2
            urlCrawler.start(216);
            while (urlCrawler.isResumable()){
                System.out.println(".....");
            }
            System.out.println("url爬取结束");
            List<String> urlList = urlCrawler.getUrlLists();
            NovelCrawler novelCrawler = new NovelCrawler("novelCrawler"+categoryId,false);
            novelCrawler.setCategoryId(categoryId);
            for (String url:urlList
                 ) {
                novelCrawler.addSeed(url);
            }

            novelCrawler.start(1);
            while (novelCrawler.isResumable()){
                System.out.println(".....");
            }
            System.out.println("novel爬取结束");
            List<Novel> novelsList= novelCrawler.getNovelsList();
            /*
            * 将小说详细信息插入数据库
            * */
            int i = 1;
            for (Novel novel:novelsList
                    ) {
                try {
                    novelServiceImpl.insertNovel(novel);
                    System.out.println("小说数"+i++);
                }catch (Exception exc){
                    throw  exc;
                    //TODO：做数据库插入失败处理 一般都是主键冲突
                }

            }
            /*
            *
            * 将章节URL和小说id插入数据库
            * */
            int num =0;
            List<Chapter> chapterList = novelCrawler.getChapterList();
            for (Chapter chapter:chapterList
                 ) {
                chapterServiceImpl.insertChapter(chapter);
                System.out.println("章节数数"+num++);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //TODO：做数据库插入失败处理
        }

        return "NovelInfo0";
    }





    /*爬章节内容*/
    @RequestMapping("/ChapterContent")
    public String ChapterContent(){
        int i = 1;
        // List<Chapter> chapters = chapterServiceImpl.findByNovelIdChapter(Long.parseLong("1534051213252"));
        List<Long> novelIs = novelServiceImpl.findAllNovelId();
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(40);

        for (Long novelId:novelIs
             ) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Chapter> chapters = chapterServiceImpl.findByNovelIdChapter(novelId);
                    for (Chapter chapter:chapters
                            ) {
                        ChapterCrawler chapterCrawler = new ChapterCrawler("chapterdb/"+"chapter"+novelId,false,chapter);
                        try {
                            chapterCrawler.start(1);

                            while (chapterCrawler.isResumable()){
                            }
                            chapter = chapterCrawler.getChapter();
                            chapterServiceImpl.updateChapter(chapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            });
            threadPoolExecutor.execute(thread);


            System.out.println("小说数"+i++);

        }
        System.out.println("ChapterContent");
     return "ChapterContent";
    }


    @RequestMapping("/NovelAll")
    public String NovelAll(){
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(6);

        for (int i=0;i<6;i++){
            Thread thread = new Thread(new Runnable() {
                int category = 0;
                @Override
                public void run() {
                    if(category>=0&&category<=5){
                        NovelInfo(category);
                    }
                }
                public Runnable setCategory(int category) {
                    this.category = category;
                    return this;
                }
            }.setCategory(i));
            threadPoolExecutor.execute(thread);
        }
        //关掉线程池
        threadPoolExecutor.shutdown();
        ChapterContent();

        return  null;
    }

}
