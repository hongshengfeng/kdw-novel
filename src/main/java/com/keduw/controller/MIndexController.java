package com.keduw.controller;

import com.keduw.model.Novel;
import com.keduw.service.NovelService;
import com.keduw.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/m")
public class MIndexController {

    @Autowired
    private NovelService novelService;

    @RequestMapping("/search")
    public String mobiSearch(HttpServletRequest request){
        return "/mobi/search";
    }

    @RequestMapping("/more")
    public String mobiCategory(HttpServletRequest request){
        return "/mobi/more";
    }

    @RequestMapping("/info/{novelId}")
    public String detailInfo(@PathVariable("novelId") String novelId, Model model){
        Novel novel = new Novel();
        int id = Parser.parserInt(novelId, 0);
        if(id > 0L){
            novel = novelService.getNovelById(id);
            model.addAttribute("novel", novel);
        }
        return "/mobi/info";
    }
}
