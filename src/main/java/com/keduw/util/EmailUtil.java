package com.keduw.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @ProjectName: novelSpider
 * @Package: com.keduw.util
 * @ClassName: EmailUtil
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/11/20/020 23:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/20/020 23:13
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EmailUtil {

    public final static  String EMAIL = "992692552@qq。com";
    public final static  String SUBJECT = "可读小说网注册";


    public static String sendEmail(String mail, JavaMailSender mailSender){

        try {
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(EMAIL);
            message.setTo(mail);
            message.setSubject(SUBJECT);
            String  identifyingCode = RandomUtil.getStringRandom(4);
            message.setText(identifyingCode);
            mailSender.send(mimeMessage);
            return identifyingCode;

        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";

        }


    }

}
