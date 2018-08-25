package com.novel.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.Config;
import com.novel.model.Chapter;
import com.novel.utils.ContentUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.crawler
 * @ClassName: ChapterCrawler
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/8/12/012 12:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/12/012 12:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ChapterCrawler extends BreadthCrawler {

    private Chapter chapter;
    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public ChapterCrawler(String crawlPath, boolean autoParse,Chapter chapter) {
        super(crawlPath, autoParse);
        this.chapter = chapter;
        this.addSeed(chapter.getChapterUrl());
        // this.addRegex(REGEX);
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.setThreads(1);
        this.setResumable(false); //停止后下次继续爬取
        this.setExecuteInterval(1000); //线程之间的等待时间
        this.setTopN(100000);
        Config.MAX_EXECUTE_COUNT = 4;
        Config.TIMEOUT_CONNECT = 4000;


    }

    public Chapter getChapter() {
        return chapter;
    }



    @Override
    public void visit(Page page, CrawlDatums next) {

        Document document = page.doc();
        Elements elements = document.select("div.bookname h1");
        String chapterName = elements.get(0).text();
        chapter.setChapter(chapterName);
        Elements contents = document.select("div#content");
        String content = ContentUtil.filterEmoji(contents.get(0).text());
        chapter.setContent(content);
        System.out.println("aaa");


    }

    public static void main(String[] args) {
        Chapter chapter = new Chapter();
        chapter.setChapterUrl("https://www.biquge5.com/0_255/159532.html");
        ChapterCrawler chapterCrawler = new ChapterCrawler("1",false,chapter);
        try {
            chapterCrawler.start(1);

            while (chapterCrawler.isResumable()){

            }
            Chapter tmp = chapterCrawler.getChapter();
            System.out.println("111");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
