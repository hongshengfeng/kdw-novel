package com.keduw.controller;

import com.keduw.model.Ipinfo;
import com.keduw.service.SeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/seo")
public class SeoController {

    @Autowired
    private SeoService seoService;

    @RequestMapping("/about")
    public String about(HttpServletRequest request, Model model){
        return "about";
    }

    @RequestMapping("/num")
    public @ResponseBody List<Ipinfo> getIpInfo(){
        List<Ipinfo> list = seoService.infoList();
        return list;
    }
}
