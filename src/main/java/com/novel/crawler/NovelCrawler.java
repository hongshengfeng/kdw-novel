package com.novel.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.novel.model.Novel;

import java.util.List;

public class NovelCrawler extends BreadthCrawler {

    private static String URL = "https://www.biquge5.com/shuku/1/allvisit-0-1.html"; // 种子页面
    private static String REGEX = "http://www.xicidaili.com/nn/[0-9]+"; // 采集规则
    public static List<Novel> novelsList;


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


        this.addSeed(URL);
        this.addRegex(REGEX);
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.setThreads(5);
        //this.setResumable(true); //停止后下次继续爬取
        this.setExecuteInterval(1000); //线程之间的等待时间
        this.setTopN(100000);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {

    }
}
