package com.example.demo.common;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Properties;

/**
 * mybatis分页插件配置
 */
@Configuration
public class MyBatisConfig {

    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper =new PageHelper();
        Properties properties=new Properties();
        //设置为true是时，会将rowbounds第一个参数offset当成pageNum页码使用
        properties.setProperty("offsetAsPageNum","true");
        //设置为true时，使用rowbounds分页会进行count查询
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        pageHelper.setProperties(properties);
        return  pageHelper;

    }



}
