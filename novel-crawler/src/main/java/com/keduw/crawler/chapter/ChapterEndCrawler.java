package com.keduw.crawler.chapter;

import com.keduw.model.Chapter;
import com.keduw.service.ChapterService;
import com.keduw.utils.JsonUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 从消息队列中获取分页章节信息
 * @author hsfeng
 * @date 2019-04-07
 */

@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.end.chapter}", autoDelete = "false"),
                exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.routing.end.chapter.key}"
        )
)
public class ChapterEndCrawler {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ChapterService chapterService;
    @Value("${mq.config.exchange}")
    private String novelExchange;
    @Value("${mq.config.routing.end.chapter.key}")
    private String chapterRouting;


    @RabbitHandler
    public void chapterNextInfo(String msg) throws Exception{
        Chapter chapter = JsonUtils.jsonToPojo(msg, Chapter.class);
        HttpClientBuilder builder = HttpClients.custom();
        builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");
        CloseableHttpClient httpclient = builder.build();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(chapter.getLink());
            //HttpHost proxy = new HttpHost(hostName,port);
            //RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10000).setSocketTimeout(10000).setConnectionRequestTimeout(3000).build();
            //httpget.setConfig(requestConfig);
            //httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
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
                //添加到消息队列，再爬取
                amqpTemplate.convertAndSend(novelExchange, chapterRouting, JsonUtils.objectToJson(chapter));
                System.out.println("next;info=" + msg);
            } else {
                chapterService.updateChapterContent(chapter);
                System.out.println("success");
            }
        }finally {
            if(response != null){
                response.close();
            }
            if(httpclient != null){
                httpclient.close();
            }
        }
    }
}
