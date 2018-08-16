package com.novel.controller;

import com.novel.model.Novel;
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
@RequestMapping("/NovelInfo")
public class ShowNovelInfoController {

    @Resource(name = "NovelService")
    private NovelServiceImpl novelServiceImpl;

    /*
    *
    * 显示全部小说详细信息
    *
    * */
    @RequestMapping("/ShowNovelAll")
    public List<Novel> ShowNovelList(){


        String  tmp =null;
        List<Novel> novelList = novelServiceImpl.findAllNovel();


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

        List<Novel> novelList = novelServiceImpl.findNovelByName(novelName);
        return novelList;
    }

    /*通过查找novelId显示小说*/
    @RequestMapping("/showNovelById")
    public List<Novel> showNovelById(@RequestParam(name = "novelName") String novelName){

        List<Novel> novelList = novelServiceImpl.findNovelByName(novelName);
        return novelList;
    }


}
