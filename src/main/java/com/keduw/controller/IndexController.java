package com.keduw.controller;

import com.keduw.jedis.JedisClient;
import com.keduw.model.Novel;
import com.keduw.service.NovelService;
import com.keduw.util.IpListUtil;
import com.keduw.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private NovelService novelService;
    @Autowired
    private JedisClient jedisClient;

    @RequestMapping("/")
    public String home(HttpServletRequest request){
        //获取访问服务器的ip并存储到redis中
        jedisClient.sadd("ip",IpListUtil.getLocalIp(request));
        return "index";
    }

    @RequestMapping("/info/{novelId}")
    public String detailInfo(@PathVariable("novelId") String novelId, Model model){
        Novel novel = new Novel();
        int id = Parser.parserInt(novelId, 0);
        if(id > 0L){
            novel = novelService.getNovelById(id);
            model.addAttribute("novel", novel);
        }
        return "info";
    }

    @RequestMapping("/category/{category}")
    public String categoryInfo(@PathVariable("category") String cate, Model model){
        return "moreInfo";
    }
}
