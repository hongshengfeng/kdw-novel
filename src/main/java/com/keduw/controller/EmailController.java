package com.keduw.controller;

import com.keduw.jedis.JedisClient;
import com.keduw.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: novelSpider
 * @Package: com.keduw.controller
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
    public String register(String mail , String code){

        String Vcode  = jedisClient.get(mail);
        if(Vcode != null && Vcode.equals(code)){
            //开始注册账号
        }

        return "succeed";
    }


}
