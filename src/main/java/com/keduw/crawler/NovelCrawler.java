package com.keduw.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.model.NovelColl;
import com.keduw.util.BaseUtil;
import com.keduw.util.CateUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 小说基础信息爬虫，更新小说章节信息，用阻塞队列存储爬取小说和对应章节信息
 */
public class NovelCrawler extends BreadthCrawler {

    private String URL = "https://www.biquge5.com"; // 种子页面
    private String REGEX = "https://www.biquge5.com/[0-9]+_[0-9]+/?"; // 采集规则
    private BlockingQueue<NovelColl> novelQueue = null;  //阻塞队列
    private Logger Log =  (Logger) LoggerFactory.getLogger(NovelCrawler.class);
    private ReentrantLock lock = null;

    public NovelCrawler(String crawlPath, boolean autoParse, BlockingQueue<NovelColl> queue, ReentrantLock lock) {
        super(crawlPath, autoParse);
        this.addSeed(URL);
        this.addRegex(REGEX);
        this.addRegex("-.*\\.(jpg|png|gif).*"); //不匹配图片
        this.addRegex("-.*#.*"); //不匹配#**的链接
        this.setThreads(10); //线程数
        this.setResumable(true); //停止后下次继续爬取
        getConf().setExecuteInterval(1000); //设置线程之间的等待时间
        getConf().setTopN(100000); //爬取URL上限
        this.novelQueue = queue;
        this.lock = lock;
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        if(page.matchUrl(REGEX)) {
            try{
                lock.lock();
                Document document = page.doc();
                Elements baseInfo = document.select("div[id=info]");
                Novel novel = new Novel();
                if(baseInfo != null && !baseInfo.isEmpty()){
                    Element info = baseInfo.get(0);
                    String title = info.getElementsByTag("h1").get(0).text(); //标题
                    String author = info.getElementsByTag("p").get(0).text(); //作者
                    String status = info.getElementsByTag("p").get(1).text(); //更新状态
                    String lastTime = info.getElementsByTag("p").get(2).text(); //最后更新时间
                    novel.setName(title);
                    novel.setAuthor(BaseUtil.tirmStr(author));
                    novel.setStatus(BaseUtil.tirmStr(status));
                    novel.setUptime(BaseUtil.tirmStr(lastTime));
                    novel.setLink(page.url());
                }
                //类别
                Elements conTop = document.select("div[class=con_top]");
                if(!conTop.isEmpty()){
                    String category = conTop.get(0).getElementsByTag("a").get(4).text();
                    novel.setCid(CateUtil.getId(category, 1));
                }
                //简介
                Elements intro = document.select("div[id=intro]");
                if(!intro.isEmpty()){
                    String brief = intro.get(0).getElementsByTag("p").get(0).html();
                    novel.setBrief(brief);
                }
                //章节
                Elements element = document.select("ul[class=_chapter] li a");
                List<Chapter> chapterList = new ArrayList<>();
                for (Element elem : element) {
                    Chapter info =  new Chapter();
                    String url =  elem.attr("href");
                    info.setId(novel.getId());
                    info.setName(elem.text());
                    info.setLink(BaseUtil.urlTrim(url));
                    chapterList.add(info);
                }
                novel.setSize(chapterList.size());
                //将爬取信息存储到队列中
                NovelColl novelColl = new NovelColl();
                novelColl.setNovel(novel);
                novelColl.setChapters(chapterList);
                novelQueue.put(novelColl);
            }catch (InterruptedException e){
                Log.error("novelCrawlerError", e.getMessage());
            }finally {
                lock.unlock();
            }
        }
    }
}
