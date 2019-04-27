package com.keduw.crawler.novel;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.model.NovelColl;
import com.keduw.utils.BaseUtil;
import com.keduw.utils.CateUtil;
import com.keduw.utils.Encoder;
import com.keduw.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 爬取小说的基本内容
 * @author hsfeng
 * @date 2019-04-07
 */
public class NovelCrawler extends BreadthCrawler {

    private String URL = "https://www.biquge5.com"; // 种子页面
    private String REGEX = "https://www.biquge5.com/[0-9]+_[0-9]+/?"; // 采集规则

    private AmqpTemplate amqpTemplate;
    private String novelExchange;
    private String novelRouting;

    public NovelCrawler(String crawlPath, boolean autoParse, AmqpTemplate amqpTemplate, String novelExchange, String novelRouting) {
        super(crawlPath, autoParse);
        this.addSeed(URL);
        this.addRegex(REGEX);
        this.addRegex("-.*\\.(jpg|png|gif).*"); //不匹配图片
        this.addRegex("-.*#.*"); //不匹配#**的链接
        this.setThreads(5); //线程数
        this.setResumable(true); //停止后下次继续爬取
        this.amqpTemplate = amqpTemplate;
        this.novelExchange = novelExchange;
        this.novelRouting = novelRouting;
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        if (page.matchUrl(REGEX)) {
            Document document = page.doc();
            Elements baseInfo = document.select("div[id=info]");
            Novel novel = new Novel();
            if (baseInfo != null && !baseInfo.isEmpty()) {
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
            if (!conTop.isEmpty()) {
                String category = conTop.get(0).getElementsByTag("a").get(4).text();
                novel.setCid(CateUtil.getId(category, 1));
            }
            //简介
            Elements intro = document.select("div[id=intro]");
            if (!intro.isEmpty()) {
                String brief = intro.get(0).getElementsByTag("p").get(0).html();
                if(StringUtils.isBlank(brief)){
                    return;
                }
                novel.setBrief(Encoder.encodeHtml(brief));
            }

            //章节
            Elements element = document.select("ul[class=_chapter] li a");
            List<Chapter> chapterList = new ArrayList<>();
            for (Element elem : element) {
                Chapter info = new Chapter();
                String url = elem.attr("href");
                info.setId(novel.getId());
                info.setName(elem.text());
                info.setLink(BaseUtil.urlTrim(url));
                chapterList.add(info);
            }
            novel.setSize(chapterList.size());
            //将爬取信息存储到消息队列中
            NovelColl novelColl = new NovelColl();
            novelColl.setNovel(novel);
            novelColl.setChapters(chapterList);
            amqpTemplate.convertAndSend(novelExchange, novelRouting, JsonUtils.objectToJson(novelColl));
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
