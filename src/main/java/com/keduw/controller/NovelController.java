package com.keduw.controller;

import com.keduw.model.Novel;
import com.keduw.service.NovelService;
import com.keduw.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 小说信息控制器
 * @author hsfeng
 */
@RestController
@RequestMapping("/novel")
public class NovelController {

    @Autowired
    private NovelService novelService;
    @Value("15")
    private int PAGE_SIZE;

    //根据类别查询小说列表
    @RequestMapping("/info/{cate}")
    public List<Novel> info(@PathVariable("cate") String cate){
        int category = Parser.parserInt(cate, 0);
        List<Novel> list = new ArrayList<Novel>();
        if(category > 0) {
            list = novelService.getNovelList(1, PAGE_SIZE, category);
        }else{
            list = novelService.getNovelList(1, PAGE_SIZE);
        }
        return list;
    }

    //分页查询小说列表
    @RequestMapping("/list/{page}")
    public List<Novel> novelList(@PathVariable("page") String page){
        int curr = Parser.parserInt(page, 1);
        List<Novel> list = novelService.getNovelList(curr, PAGE_SIZE);
        return list;
    }

    //最新小说
    @RequestMapping("/new")
    public List<Novel> newNovelInfo(){
        List<Novel> list = novelService.getNewInfo();
        return list;
    }

    //热门小说
    @RequestMapping("/hot")
    public List<Novel> hotNovelInfo(){
        List<Novel> list = novelService.getHotInfo();
        return list;
    }
}
