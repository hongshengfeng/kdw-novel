package com.novel.controller;

import com.novel.model.Novel;
import com.novel.service.serviceImpl.NovelService;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Resource(name = "NovelService")
    private NovelService novelService;

    /*
    *
    * 显示全部小说详细信息
    *
    * */
    @RequestMapping("/ShowNovelInfo")
    public List<Novel> ShowNovelList(){


        String  tmp =null;
        List<Novel> novelList = novelService.findAllNovel();


        return novelList;
    }


}
