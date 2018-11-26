package com.keduw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NovelApplicationTests {
	@Autowired
	JavaMailSender mailSender;

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
}
