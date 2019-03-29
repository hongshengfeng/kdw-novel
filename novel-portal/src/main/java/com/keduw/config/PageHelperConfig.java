package com.keduw.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageHelperConfig {
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties prop = new Properties();
        prop.setProperty("offsetAsPageNum", "true");
        prop.setProperty("rowBoundsWithCount", "true");
        prop.setProperty("reasonable", "true");
        prop.setProperty("dialect", "mysql");
        prop.setProperty("supportMethodsArguments", "false");
        prop.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(prop);
        return pageHelper;
    }
}
