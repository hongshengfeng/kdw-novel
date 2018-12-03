package com.keduw;

import com.keduw.model.User;
import com.keduw.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NovelApplicationTests {
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	public UserService userService;


	@Test
	public void contextLoads() {
	}
	@Test
	public void testMail(){
		try {
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setFrom("992692552@qq。com");
			message.setTo("915869445@qq.com");
			message.setSubject("测试邮件主题");
			message.setText("测试邮件内容");
			this.mailSender.send(mimeMessage);


		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}
	@Test
	public void testAdduser(){

		User user = new User();
		user.setName("1123");
		user.setPwd("1123");
		user.setSacct("234");
		Date date = new Date();
		user.setRegistTime(date);
	    userService.addUser(user);

	}
}
