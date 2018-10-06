package com.keduw.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.keduw.model.Chapter;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 小说章节内容爬取
 */
public class ChapterCrawler extends BreadthCrawler {

    private Chapter chapter;
    private String REGEX = "https://www.biquge5.com/[0-9]+_[0-9]+/[0-9]+.html"; // 采集规则

    public ChapterCrawler(String crawlPath, boolean autoParse, Chapter curr) {
        super(crawlPath, autoParse);
        this.chapter = curr;
        this.addSeed(chapter.getChapterUrl());
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.setThreads(1);
        this.setResumable(false); //停止后下次继续爬取
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        if(page.matchUrl(REGEX)) {
            Document document = page.doc();
            Elements contents = document.select("div[id=content]");
            String content = contents.get(0).html();
            chapter.setContent(content);
        }
    }
}
