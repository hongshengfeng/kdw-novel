package com.keduw.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.keduw.model.Chapter;

/**
 * 小说章节内容爬取
 */
public class ChapterCrawler extends BreadthCrawler {

    public ChapterCrawler(String crawlPath, boolean autoParse, Chapter chapter) {
        super(crawlPath, autoParse);
        this.addSeed(chapter.getChapterUrl());
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {

    }
}
