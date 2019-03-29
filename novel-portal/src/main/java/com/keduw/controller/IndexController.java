package com.keduw.controller;

import com.keduw.model.Novel;
import com.keduw.service.NovelService;
import com.keduw.utils.BaseUtil;
import com.keduw.utils.IpListUtil;
import com.keduw.utils.JedisClient;
import com.keduw.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("user_ip")
    private String keys;

    @RequestMapping("/")
    public String index(HttpServletRequest request){
        //获取访问电脑版网站的ip
        String ip = IpListUtil.getLocalIp(request);
        String fields = "site_" + ip;
        if(jedisClient.hget(keys, fields) == null){
            jedisClient.hset(keys, fields, ip);
        }
        if(BaseUtil.isMoblie(request)){
            return "/mobi/index";
        }
        return "index";
    }

    @RequestMapping("/info/{novelId}")
    public String detailInfo(@PathVariable("novelId") String novelId, Model model){
        Novel novel = new Novel();
        int id = Parser.parserInt(novelId, 0);
        if(id > 0){
            novel = novelService.getNovelById(id);
        }
        model.addAttribute("novel", novel);
        return "info";
    }

    @RequestMapping("/category")
    public String categoryInfo(HttpServletRequest request){
        return "moreInfo";
    }

    @RequestMapping("/search")
    public String searchNovel(HttpServletRequest request, Model model){
        String wd = request.getParameter("wd");
        model.addAttribute("wd", wd);
        return "search";
    }

}
