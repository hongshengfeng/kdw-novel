package com.keduw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRabbit
@MapperScan("com.keduw.dao")
public class CrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}

}
