package com.keduw.crawler;

import com.keduw.model.User;
import com.keduw.service.UserService;
import com.keduw.utils.JedisClient;
import com.keduw.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @ProjectName: novelSpider
 * @Package: com.keduw.crawler
 * @ClassName: EmailController
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/11/21/021 23:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/21/021 23:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    JedisClient jedisClient;
    @Autowired
    UserService userService;
    
    @RequestMapping("/sendEmail")
    public String sendEmail(String mail , HttpServletRequest request){

        String result = EmailUtil.sendEmail(mail,javaMailSender);
        if(!result.equals("error")){
            jedisClient.set(mail,result);
            jedisClient.expire(mail,300);

        }
        return "succeed";
    }

    @RequestMapping("/register")
    public String register(String mail ,String pwd,String name,String code){

        String Vcode  = jedisClient.get(mail);
        if(Vcode != null && Vcode.equals(code)){
            User user = new User();
            Date date = new Date();
            user.setSacct(mail);
            user.setName(name);
            user.setPwd(pwd);
            user.setRegistTime(date);

            //开始注册账号
            int flag = userService.addUser(user);
            if(flag <= 0){
                return  "error";
            }

        }

        return "succeed";
    }


}
