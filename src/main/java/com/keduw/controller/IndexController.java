package com.keduw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/info/{novelId}")
    public String detailInfo(@PathVariable("novelId") String novelId, Model model){
        return "info";
    }

    @RequestMapping("/category/{category}")
    public String categoryInfo(@PathVariable("category") String cate, Model model){
        return "details";
    }
}
