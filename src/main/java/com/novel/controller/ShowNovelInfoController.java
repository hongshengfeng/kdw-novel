package com.novel.controller;

import com.github.pagehelper.PageInfo;
import com.novel.model.Chapter;
import com.novel.model.Novel;
import com.novel.service.serviceImpl.ChapterServiceImpl;
import com.novel.service.serviceImpl.NovelServiceImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.controller
 * @ClassName: ShowNovelInfoController
 * @Description: java类作用描述:显示所有小说信息
 * @Author: 林浩东
 * @CreateDate: 2018/8/15/015 23:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/15/015 23:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Controller
@RequestMapping("/ShowNovelInfo")
public class ShowNovelInfoController {

    @Resource
    private NovelServiceImpl novelService;
    @Resource
    private ChapterServiceImpl chapterService;

    /*
    *
    * 显示全部小说列表
    *
    * */
    @RequestMapping("/ShowNovelAll")
    public String ShowNovelList(HttpServletRequest request){
        List<Novel> novelList = novelService.findAllNovel();
        request.setAttribute("novelList",novelList);
        return "novelList";
    }


    /*
    *
    *
    * 通过查找关键字显示小说
    *
    * */
    @RequestMapping("/showNovelByName")
    public List<Novel> showNovelByName(@RequestParam(name = "novelName") String novelName){

        List<Novel> novelList = novelService.findNovelByName(novelName);
        return novelList;
    }

    /*通过查找novelId显示小说*/
    @RequestMapping("/showNovelByNovelId")
    public Novel showNovelByNovelId(@RequestParam(name = "novelId") long novelId){

        Novel novel = novelService.findNovelById(novelId);
        return novel;
    }
    /*通过查找novelId显示小说章节列表*/
    @RequestMapping("/showChapterlByNovelId")
    public List<Chapter> showChapterlByNovelId(@RequestParam(name = "novelId") long novelId){

        List<Chapter> chapterList = chapterService.findByNovelIdChapter(novelId);
        return chapterList;
    }
    /*显示小说信息*/
    @RequestMapping("/showNovel")
    @Cacheable(value = "novelId")
    public String showNovel(HttpServletRequest request,@RequestParam(name="novelId")long novelId){

       Novel novel = novelService.findNovelById(novelId);
       System.out.println("1111");
       if (!StringUtils.isEmpty(novel)){
           request.setAttribute("novel",novel);
           request.setAttribute("category",novel.getCategory());
       }
        return "novelInfo";

    }
    /*测试*/
    @RequestMapping("/index")
    public String test(@RequestParam(name="pageId") int pageId){
      /*  Novel novel = new Novel();
        novel.setCategoryId(0);
        List<Novel> list = novelService.queryNovelList(novel);
        System.currentTimeMillis();*/
       PageInfo<Novel> pageInfo = novelService.selectAll(1,10);
       System.out.println(pageInfo.toString());
       return "user";


    }


}
