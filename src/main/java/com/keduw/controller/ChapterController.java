package com.keduw.controller;

import com.keduw.model.Chapter;
import com.keduw.service.ChapterService;
import com.keduw.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 章节信息
 */
@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/list/{id}")
    public List<Chapter> infoList(@PathVariable("id") String id){
        int novelId = Parser.parserInt(id, 0);
        List<Chapter> chapters = new ArrayList<>();
        if(novelId > 0){
            chapters = chapterService.getChapterList(novelId);
        }
        return chapters;
    }
}
