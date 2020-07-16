package com.ryan.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ryan
 * @date 2020/7/14 11:03
 */
@Configuration
@MapperScan("com.ryan.dao")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor createPaginationInterceptor(){
        return new PaginationInterceptor();
    }
}
