package com.keduw.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.keduw.model.Chapter;
import com.keduw.service.ChapterService;
import com.keduw.util.JsonUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.BlockingQueue;

/**
 * 小说章节内容爬取
 */
public class ChapterCrawler extends BreadthCrawler {

    private Chapter chapter;
    private String REGEX = "https://www.biquge5.com/[0-9]+_[0-9]+/[0-9]+.+.html"; // 采集规则
    private BlockingQueue<Chapter> chapterQueue = null; // 有多页面则替换链接后存储到该队列
    private BlockingQueue<Chapter> updateQueue = null;

    public ChapterCrawler(String crawlPath, boolean autoParse, Chapter curr, BlockingQueue<Chapter> queue, BlockingQueue<Chapter> updateQueue) {
        super(crawlPath, autoParse);
        this.addSeed(curr.getLink());
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.setThreads(10);
        this.setResumable(false); //停止后下次继续爬取
        this.chapter = curr;
        this.chapterQueue = queue;
        this.updateQueue = updateQueue;
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        if(page.matchUrl(REGEX)) {
            Document document = page.doc();
            Elements contents = document.select("div[id=content]");
            String content = contents.get(0).html();
            //同一章节分多页则拼接内容
            String preContent = chapter.getContent();
            if(preContent != null){
                content = preContent + content;
            }
            chapter.setContent(content);
            //存在下一页则表示该章节内容还没完，放到队列里继续爬
            Elements nextPage = document.select("div[class=bottem1]");
            Element element = nextPage.get(0).getElementsByTag("a").get(4);
            String nextContent = element.text();
            if(nextContent.equals("下一页")) {
                // 有下一页则进行内容替换和存储到待继续爬取的队列里
                String nextUrl = element.attr("href");
                String preUrl = chapter.getLink();
                nextUrl = preUrl.substring(0, preUrl.lastIndexOf("/") + 1) + nextUrl;
                chapter.setLink(nextUrl);
                try {
                    chapterQueue.put(chapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    updateQueue.put(chapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
