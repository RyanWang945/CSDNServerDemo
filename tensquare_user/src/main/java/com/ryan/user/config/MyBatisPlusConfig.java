package com.ryan.user.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ryan
 * @date 2020/7/10 11:28
 */
@Configuration
@MapperScan("com.ryan.user.dao")
public class MyBatisPlusConfig {
    @Bean
    public PaginationInterceptor createPaginationInterceptor(){
        return new PaginationInterceptor();
    }
}
