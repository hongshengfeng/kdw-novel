package com.keduw.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.service.ChapterService;
import com.keduw.util.ApplicationUtil;
import com.keduw.util.BaseUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 小说章节检查是否有更新
 */
public class CheckCrawler extends BreadthCrawler {

    private Novel novel;
    private String REGEX = "https://www.biquge5.com/[0-9]+_[0-9]+/[0-9]+.html"; // 采集规则

    public CheckCrawler(String crawlPath, boolean autoParse, Novel curr) {
        super(crawlPath, autoParse);
        this.novel = curr;
        this.addSeed(novel.getNovelUrl());
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.setThreads(1);
        this.setResumable(false); //停止后下次继续爬取
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        if(page.matchUrl(REGEX)) {
            Document document = page.doc();
            Elements chapter = document.select("ul[class=_chapter] li a");
            List<Chapter> chapterList = new ArrayList<>();
            //章节列表
            for (Element element : chapter) {
                Chapter info = new Chapter();
                String url = element.attr("href");
                String content = element.text();
                info.setNovelId(novel.getNovelId());
                info.setChapter(content);
                info.setChapterUrl(BaseUtil.urlTrim(url));
                chapterList.add(info);
            }
            System.out.println(chapterList.size());
            //更新章节信息列表
            if (chapterList.size() > novel.getChapterSize()) {
                ChapterService chapterService = (ChapterService) ApplicationUtil.getBean("chapterService");
                chapterService.updateChapter(chapterList);
            }
        }
    }
}
