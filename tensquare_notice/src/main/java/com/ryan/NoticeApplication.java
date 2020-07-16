package com.ryan;

import com.ryan.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author Ryan
 * @date 2020/7/14 10:53
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class NoticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoticeApplication.class,args);
    }
    @Bean
    public IdWorker createIdWorker(){
        return new IdWorker(1,1);
    }
}
