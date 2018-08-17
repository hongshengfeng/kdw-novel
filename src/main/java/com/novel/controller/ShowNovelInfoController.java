package com.novel.controller;

import com.novel.model.Chapter;
import com.novel.model.Novel;
import com.novel.service.serviceImpl.ChapterServiceImpl;
import com.novel.service.serviceImpl.NovelServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RestController
@RequestMapping("/ShowNovelInfo")
public class ShowNovelInfoController {

    @Resource
    private NovelServiceImpl novelService;
    @Resource
    private ChapterServiceImpl chapterService;

    /*
    *
    * 显示全部小说详细信息
    *
    * */
    @RequestMapping("/ShowNovelAll")
    public List<Novel> ShowNovelList(){


        String  tmp =null;
        List<Novel> novelList = novelService.findAllNovel();

        return novelList;
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


}
