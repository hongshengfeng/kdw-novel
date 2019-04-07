package com.keduw.crawler;

import com.keduw.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//会员登陆后会记录用户的阅读记录
@Controller
@RequestMapping("/user")
@PropertySource("classpath:cache.properties")
public class UserController {

    @Value("user_ip")
    private String session;

    @RequestMapping("/login")
    public @ResponseBody String isLogin(HttpServletRequest request){
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        User user = new User();
        request.getSession().setAttribute(session, user);
        return null;
    }
}
