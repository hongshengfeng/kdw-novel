package com.keduw.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.util.ApplicationUtil;
import com.keduw.util.BaseUtil;
import com.keduw.util.Encoder;
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
    private BlockingQueue<List<Chapter>> queue = null;

    public CheckCrawler(String crawlPath, boolean autoParse, Novel curr, BlockingQueue<List<Chapter>> queue) {
        super(crawlPath, autoParse);
        this.novel = curr;
        this.addSeed(novel.getLink());
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.setThreads(1);
        this.setResumable(true); //停止后下次继续爬取
        this.queue = queue;
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        if(page.matchUrl(REGEX)) {
            Document document = page.doc();
            Elements chapter = document.select("ul[class=_chapter] li a");
            //章节列表
            List<Chapter> chapterList = new ArrayList<Chapter>();
            int nId = novel.getId();
            for (Element element : chapter) {
                Chapter info = new Chapter();
                String url = Encoder.encodeUrl(element.attr("href"));
                String content = Encoder.encodeHtml(element.text());
                info.setId(nId);
                info.setName(content);
                info.setLink(url);
                chapterList.add(info);
            }
            NovelService novelService = (NovelService) ApplicationUtil.getBean("novelService");
            try{
                int size = novelService.getNovelSize(nId);
                //大小有变化则加入消费队列
                if(size != chapterList.size()){
                    queue.put(chapterList);
                }
            }catch (InterruptedException e){
                Log.error("getChapterError", e.getMessage());
            }
        }
    }
}
