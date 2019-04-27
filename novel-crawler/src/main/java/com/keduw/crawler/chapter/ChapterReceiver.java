package com.keduw.crawler.chapter;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.model.NovelColl;
import com.keduw.service.NovelService;
import com.keduw.utils.Encoder;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 从消息队列中获取小说爬取的章节和基本信息
 * 日常更新检查，有变化添加到小说队列里
 * @author hsfeng
 * @date 2019-04-07
 */

@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.chapter.update}", autoDelete = "false"),
                exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.routing.chapter.update.key}"
        )
)
public class ChapterReceiver {

    @Autowired
    private NovelService novelService;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${mq.config.exchange}")
    private String novelExchange;
    @Value("${mq.config.routing.novel.key}")
    private String novelRouting;

    @RabbitHandler
    public void novelInfo(String msg) throws Exception{
        Novel novel = JsonUtils.jsonToPojo(msg, Novel.class);
        HttpClientBuilder builder = HttpClients.custom();
        builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");
        CloseableHttpClient httpclient = builder.build();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(novel.getLink());
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
            int size = novelService.getNovelSize(nId);
            //大小有变化则加入消费队列
            if(size != chapterList.size()){
                NovelColl novelColl = new NovelColl();
                novelColl.setNovel(novel);
                novelColl.setChapters(chapterList);
                amqpTemplate.convertAndSend(novelExchange, novelRouting, JsonUtils.objectToJson(novelColl));
            }
        }catch (Exception e){
            logger.error("crawel chapter err;", e.getMessage());
        }finally {
            if(response != null){
                response.close();
            }
            if(httpclient != null){
                httpclient.close();
            }
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ChapterReceiver.class);
}
