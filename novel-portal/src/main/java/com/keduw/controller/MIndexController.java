package com.keduw.controller;

import com.keduw.model.Chapter;
import com.keduw.model.Novel;
import com.keduw.service.ChapterService;
import com.keduw.service.NovelService;
import com.keduw.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/m")
public class MIndexController {

    @Autowired
    private NovelService novelService;
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/search")
    public String mobiSearch(){
        return "mobi/search";
    }

    @RequestMapping("/more")
    public String mobiCategory(){
        return "mobi/more";
    }

    @RequestMapping("/info/{nId}/{cId}")
    public String detailInfo(@PathVariable("nId")String nId, @PathVariable("cId")String cId, Model model){
        int novelId = Parser.parserInt(nId, 0);
        int chapterId = Parser.parserInt(cId, 0);
        if(novelId == 0 || chapterId == 0){
            return "index";
        }
        //小说标题
        Novel novel = novelService.getNovelById(novelId);
        List<Chapter> list = chapterService.getChapterList(novelId);
        int start = 0, end = 0; //第一章和最后一章的id
        if(list != null){
            start = list.get(0).getId();
            end = list.get(list.size() - 1).getId();
        }
        String title = novel != null ? novel.getName() : "可读小说";
        model.addAttribute("nId", novelId);
        model.addAttribute("cId", chapterId);
        model.addAttribute("title", title);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "mobi/info";
    }

    @RequestMapping("/chapter/{nId}")
    public String chapterInfo(@PathVariable("nId") String nId, Model model){
        int id = Parser.parserInt(nId, 0);
        Novel novel = new Novel();
        if(id > 0){
            novel = novelService.getNovelById(id);
        }
        model.addAttribute("id", id);
        model.addAttribute("novel", novel);
        return "mobi/chapter";
    }
}
