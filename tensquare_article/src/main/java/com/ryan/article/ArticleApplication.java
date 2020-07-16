package com.ryan.article;

import com.ryan.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author Ryan
 * @date 2020/7/10 9:08
 */
@SpringBootApplication
@MapperScan("com.ryan.article.dao")
@EnableFeignClients
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class,args);
    }
    @Bean
    public IdWorker createIdWorker(){
        return new IdWorker(1,1);
    }
}
