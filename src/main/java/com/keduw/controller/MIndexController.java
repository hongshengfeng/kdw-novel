package com.keduw.controller;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m")
public class MIndexController {

    @Autowired
    private NovelService novelService;
    @Autowired
    private ChapterService chapterService;

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
        int id = Parser.parserInt(novelId, 0);
        int firstCId = 0;
        Novel novel = new Novel();
        if(id > 0){
            novel = novelService.getNovelById(id);
            List<Chapter> list = chapterService.getChapterList(id);
            if(list != null && list.size() > 0){
                firstCId = list.get(0).getId();
            }
        }
        model.addAttribute("firstCId", firstCId);
        model.addAttribute("novel", novel);
        return "/mobi/info";
    }

    @RequestMapping("/chapter/{novelId}")
    public String chapterInfo(@PathVariable("novelId") String novelId, Model model){
        int id = Parser.parserInt(novelId, 0);
        Novel novel = new Novel();
        if(id > 0){
            novel = novelService.getNovelById(id);
        }
        model.addAttribute("id", id);
        model.addAttribute("novel", novel);
        return "/mobi/chapter";
    }
}
