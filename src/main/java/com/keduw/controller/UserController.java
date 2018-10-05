package com.keduw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员，登陆后会记录用户的阅读记录
 * 记录采用前端cookie实现，登录后保存阅读记录到cookie，不登录则不保存
 */
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public @ResponseBody String isLogin(HttpServletRequest request){
        return null;
    }
}
