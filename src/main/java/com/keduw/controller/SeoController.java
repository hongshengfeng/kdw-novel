package com.keduw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seo")
public class SeoController {

    @RequestMapping("/about")
    public String about(){
        return "about";
    }

    @RequestMapping("/cooperate")
    public String cooperate(){
        return "cooperate";
    }
}
