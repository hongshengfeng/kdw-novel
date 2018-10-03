package com.keduw.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.Config;
import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.util.IdUtil;
import com.keduw.util.SplitUtil;
import com.keduw.util.UrlUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NovelCrawler extends BreadthCrawler {

    private static String URL = "https://www.biquge5.com/16_16836/"; // 种子页面
    private static String REGEX = "https://www.biquge5.com/[0-9]+_[0-9]+/?"; // 采集规则
    private   List<Novel> novelsList;
    private   List<Chapter> ChapterList;
    private  int categoryId = 0;


    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public NovelCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        //this.addSeed(URL);
       // this.addRegex(REGEX);
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.setThreads(10);
        this.setResumable(false); //停止后下次继续爬取
        this.setExecuteInterval(1000); //线程之间的等待时间
        this.setTopN(100000);
        Config.MAX_EXECUTE_COUNT = 3;
        Config.TIMEOUT_CONNECT = 4000;

        novelsList = Collections.synchronizedList(new ArrayList<>());
        ChapterList = Collections.synchronizedList(new ArrayList<>());
    }

    public  List<Novel> getNovelsList() {
        return novelsList;
    }

    public List<Chapter> getChapterList() {
        return ChapterList;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        if(page.matchUrl(REGEX)){
            Novel novel = new Novel();
            novel.setNovelid(IdUtil.getId());
            Document document = page.doc();
            Elements elements = document.select("div#info");
            if(!elements.isEmpty()){
                Element e = elements.get(0);
                String title = e.getElementsByTag("h1").get(0).text();
                System.out.println("title:"+title);
                String author = e.getElementsByTag("p").get(0).text();
                String status = e.getElementsByTag("p").get(1).text();
                String lastTime = e.getElementsByTag("p").get(2).text();
                novel.setNovelname(title);
                novel.setAuthor(SplitUtil.tirmStr(author));
                novel.setStatus(SplitUtil.tirmStr(status));
                novel.setLasttime(SplitUtil.tirmStr(lastTime));
                novel.setNovelurl(page.getUrl());
            }
            Elements intro = document.select("div#intro");
            if(!intro.isEmpty()){
                Element e = intro.get(0);
                String brief = e.getElementsByTag("p").get(0).text();
                novel.setBrief(brief);
            }
            Elements chapter = document.select("ul._chapter li a");
            int num = 0;
            for (Element e:chapter
                 ) {
                 Chapter chapterTmp =  new Chapter();
                 String url =  e.attr("href");
                 chapterTmp.setNovelid(novel.getNovelid());
                 chapterTmp.setChapterurl(UrlUtil.Urltrim(url));
                 ChapterList.add(chapterTmp);
                 num++;
            }
            novel.setChaptersize(num);
            novel.setCategoryid(categoryId);
            novelsList.add(novel);
        }
    }
}
