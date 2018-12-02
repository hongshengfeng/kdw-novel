package com.keduw.crawler;

import com.keduw.model.Chapter;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

//小说内容爬虫线程
public class NovelCrawelThread implements Runnable{

    private BlockingQueue<Chapter> chapterQueue = null;
    private BlockingQueue<Chapter> updateQueue = null;
    private ReentrantLock lock = new ReentrantLock();
    private Logger Log =  (Logger) LoggerFactory.getLogger(NovelCrawelThread.class);

    NovelCrawelThread(BlockingQueue<Chapter> chapterQueue, BlockingQueue<Chapter> updateQueue){
        this.chapterQueue  = chapterQueue;
        this.updateQueue = updateQueue;
    }

    @Override
    public void run() {
        lock.lock();
        try{
            int time = 0;
            while (time < 3){
                if(chapterQueue != null && chapterQueue.size() >= 0) {
                    time = 0;
                    Chapter chapter = chapterQueue.poll(1000, TimeUnit.MILLISECONDS);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    CloseableHttpResponse response = null;
                    try {
                        HttpGet httpget = new HttpGet(chapter.getLink());
                        HttpHost proxy = new HttpHost("47.101.190.246",8080);
                        RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10000).setSocketTimeout(10000).setConnectionRequestTimeout(3000).build();
                        httpget.setConfig(requestConfig);
                        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
                        response = httpclient.execute(httpget); //执行
                        int code = response.getStatusLine().getStatusCode(); //获取响应状态码
                        String html = "";
                        if (code == 200) {
                            html = EntityUtils.toString(response.getEntity(), "utf-8");
                        } else {
                            EntityUtils.consume(response.getEntity());
                        }
                        if ("".equals(html)) {
                            return;
                        }
                        // 解析数据
                        Document document = Jsoup.parse(html);
                        Elements contents = document.select("div[id=content]");
                        String content = contents.get(0).html();
                        //同一章节分多页则拼接内容
                        String preContent = chapter.getContent();
                        if (preContent != null) {
                            content = preContent + content;
                        }
                        chapter.setContent(content);
                        Elements nextPage = document.select("div[class=bottem1]");
                        Element element = nextPage.get(0).getElementsByTag("a").get(4);
                        String nextContent = element.text();
                        if (nextContent.equals("下一页")) {
                            String nextUrl = element.attr("href");
                            String preUrl = chapter.getLink();
                            nextUrl = preUrl.substring(0, preUrl.lastIndexOf("/") + 1) + nextUrl;
                            chapter.setLink(nextUrl);
                            chapterQueue.put(chapter);
                        } else {
                            updateQueue.put(chapter);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if(response != null){
                            response.close();
                        }
                        if(httpclient != null){
                            httpclient.close();
                        }
                    }

                    StringBuffer str = new StringBuffer();
                    str.append("chapterQueue:");
                    str.append(chapterQueue.size());
                    str.append("updateQueue:");
                    str.append(updateQueue.size());
                    System.out.println(str.toString());
                }else{
                    Thread.sleep(5000);
                    time ++;
                }
            }
        }catch (Exception e){
            Log.error("crawelThreadError", e);
        }finally {
            lock.unlock();
        }
    }
}
