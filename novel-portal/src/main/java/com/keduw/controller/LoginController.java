package com.keduw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: novelSpider
 * @Package: com.keduw.controller
 * @ClassName: LoginController
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2019/3/26/026 17:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/3/26/026 17:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(){
        System.out.println("test");
        return "login";
    }
}
