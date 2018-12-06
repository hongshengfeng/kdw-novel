package com.keduw.controller;

import com.keduw.model.Category;
import com.keduw.model.Novel;
import com.keduw.service.CategoryService;
import com.keduw.service.NovelService;
import com.keduw.util.BaseUtil;
import com.keduw.util.Page;
import com.keduw.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 小说信息控制器
 * @author hsfeng
 */
@RestController
@RequestMapping("/novel")
public class NovelController {

    @Autowired
    private NovelService novelService;
    @Autowired
    private CategoryService categoryService;
    @Value("18")
    private int PAGE_SIZE;

    //根据类别查询小说列表
    @RequestMapping("/info/{cate}")
    public List<Novel> info(@PathVariable("cate") String cate){
        int cId = Parser.parserInt(cate, 0);
        // 获取小说总数
        int counts = cId == 0 ? novelService.getNovelCount() : novelService.getNovelCountByCategory(cId);
        int curr = BaseUtil.betweenRandom(counts - PAGE_SIZE);
        List<Novel> list = new ArrayList<Novel>();
        if(cId > 0) {
            list = novelService.getNovelList(curr, PAGE_SIZE, cId);
        }else{
            List<Category> infoList = categoryService.getInfoList();
            List<Novel> allList = new ArrayList<Novel>();
            for(Category info : infoList){
                int id = info.getId();
                allList.addAll(novelService.getNovelList(curr, PAGE_SIZE, id));
            }
            if(allList.size() > 0){
                //洗牌，打乱原来的顺序
                Collections.shuffle(allList);
                Random random = new Random();
                int index = random.nextInt(allList.size() - PAGE_SIZE);
                list = allList.subList(index, index + PAGE_SIZE);
            }
        }
        return list;
    }

    //分页查询小说列表
    @RequestMapping("/list/{category}/{page}")
    public List<Novel> novelList(@PathVariable("category") String category, @PathVariable("page") String page){
        int curr = Parser.parserInt(page, 1);
        int categoryId = Parser.parserInt(category, 0);
        List<Novel> list = new ArrayList<Novel>();
        if(categoryId > 0){
            list = novelService.getNovelList(curr, PAGE_SIZE, categoryId);
        }else{
            list = novelService.getNovelList(curr, PAGE_SIZE);
        }
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

    //获取小说总数
    @RequestMapping("/counts/{category}")
    public int counts(@PathVariable("category")String category){
        int categoryId = Parser.parserInt(category, 0);
        int counts = 0;
        if(categoryId > 0){
            counts = novelService.getNovelCountByCategory(categoryId);
        }else{
            counts = novelService.getNovelCount();
        }
        return counts;
    }

    //小说搜索
    @RequestMapping("/search/{wd}/{curr}")
    public Page<Novel> search(@PathVariable("wd")String wd, @PathVariable("curr")String curr){
        int start = Parser.parserInt(curr, 1);
        return novelService.getNovelByName(wd, start);
    }
}
