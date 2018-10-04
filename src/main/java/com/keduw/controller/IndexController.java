package com.keduw.controller;

import com.keduw.model.Novel;
import com.keduw.service.NovelService;
import com.keduw.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private NovelService novelService;

    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @RequestMapping("/info/{novelId}")
    public String detailInfo(@PathVariable("novelId") String novelId, Model model){
        Novel novel = new Novel();
        Long id = Parser.parserLong(novelId, 0L);
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
