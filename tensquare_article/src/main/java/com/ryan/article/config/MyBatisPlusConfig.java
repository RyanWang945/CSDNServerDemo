package com.ryan.article.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ryan
 * @date 2020/7/10 11:28
 */
@Configuration
public class MyBatisPlusConfig {
    @Bean
    public PaginationInterceptor createPaginationInterceptor(){
        return new PaginationInterceptor();
    }
}
