package com.novel;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;



@SpringBootApplication
@MapperScan("com.novel.dao")
@ComponentScan
@EnableScheduling
public class NovelApplication {


    public static void main(String[] args){

        SpringApplication.run(NovelApplication.class,args);


    }

}
