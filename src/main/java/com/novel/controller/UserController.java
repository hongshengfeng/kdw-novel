package com.novel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: novelSpider
 * @Package: com.novel.controller
 * @ClassName: UserController
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/8/25/025 15:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/8/25/025 15:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Controller
@RequestMapping("/test")
public class UserController {




        @RequestMapping("/user")
        public String user(HttpServletRequest request){

           request.setAttribute("user","hello thymeleaf");
            List<String> list1=new ArrayList<>();
            for (int i = 0; i <10 ; i++) {
                list1.add(""+i);
            }
            request.setAttribute("list",list1);
            request.setAttribute("user","hello thymeleaf");
            request.getSession().setAttribute("shj","帅哥");
            return "user";
        }

}
