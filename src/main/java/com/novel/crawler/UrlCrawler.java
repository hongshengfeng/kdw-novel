package com.novel.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCrawler extends BreadthCrawler {
    //存放小说的url；
    private List<String> urlLists;
    private String seed = "https://www.biquge5.com/shuku/1/allvisit-0-1.html";
    private String regex = "https://www.biquge5.com/shuku/1/allvisit-0-[0-9]+.html";

    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public UrlCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        addSeed(seed);
        addRegex(regex);

        setThreads(50);
        setTopN(1000);
        setResumable(false);
        this.setExecuteInterval(1000);
        urlLists = new ArrayList<>();


    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        if (page.matchUrl(regex)){
            Document document = page.doc();
            //获取小说链接
            Elements elements = document.select("div.col-md-5.col-sm-4.col-xs-9.text-overflow a");
            for (Element e:elements
                    ) {
                  urlLists.add(e.attr("href"));

            }
            Elements nextPageUrl = document.select("a.btn-primary");
            for (Element e:nextPageUrl
                    ) {
                //获取下个页面url
                String tmp  =  e.attr("abs:href");
                if (tmp!=null){
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(tmp);
                    String nextPage = m.replaceAll("");
                    next.add(nextPage);
                }


            }

        }





    }

    public static void main(String[] args) {
        UrlCrawler urlCrawler = new UrlCrawler("test",true);
        try {
            urlCrawler.start(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
