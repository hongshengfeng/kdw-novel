package com.keduw.util;

import com.keduw.model.Chapter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.BlockingQueue;

//适配小说章节的内容的爬虫
//考虑到指定url爬取不适合webCollecter,这里采用httpClient和jsoup
public class CrawelUtil {

    public static void getDomInfo(Chapter chapter, BlockingQueue<Chapter> chapterQueue, BlockingQueue<Chapter> updateQueue) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try{
            HttpGet httpget = new HttpGet(chapter.getLink());
            httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            response = httpclient.execute(httpget); //执行
            int code = response.getStatusLine().getStatusCode(); //获取响应状态码
            String html = "";
            if(code == 200){
                html = EntityUtils.toString (response.getEntity(),"utf-8");
            }else{
                EntityUtils.consume(response.getEntity());
            }
            if("".equals(html)){
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
            }else{
                updateQueue.put(chapter);
            }
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
    }
}
