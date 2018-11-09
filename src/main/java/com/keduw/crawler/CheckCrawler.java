package com.keduw.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.util.BaseUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 小说章节检查是否有更新
 */
public class CheckCrawler extends BreadthCrawler {

    private Novel novel;
    private String REGEX = "https://www.biquge5.com/[0-9]+_[0-9]+/[0-9]+.html"; // 采集规则
    private static Logger Log =  (Logger) LoggerFactory.getLogger(CheckCrawler.class);
    BlockingQueue<Chapter> queue = null;

    public CheckCrawler(String crawlPath, boolean autoParse, Novel curr, BlockingQueue<Chapter> queue) {
        super(crawlPath, autoParse);
        this.novel = curr;
        this.addSeed(novel.getLink());
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.setThreads(1);
        this.setResumable(false); //停止后下次继续爬取
        this.queue = queue;
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        if(page.matchUrl(REGEX)) {
            Document document = page.doc();
            Elements chapter = document.select("ul[class=_chapter] li a");
            //章节列表
            for (Element element : chapter) {
                Chapter info = new Chapter();
                String url = element.attr("href");
                String content = element.text();
                info.setId(novel.getId());
                info.setName(content);
                info.setLink(BaseUtil.urlTrim(url));
                try{
                    queue.put(info);
                }catch (InterruptedException e){
                    Log.error("getChapterError", e.getMessage());
                }
            }
        }
    }
}
