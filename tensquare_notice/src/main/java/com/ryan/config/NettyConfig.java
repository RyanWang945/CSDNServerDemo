package com.ryan.config;

import com.ryan.netty.NettyServer;
import netscape.security.UserTarget;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ryan
 * @date 2020/7/16 12:30
 */
@Configuration
public class NettyConfig {
    @Bean
    public NettyServer createNettyServer(){
        NettyServer nettyServer = new NettyServer();
        //启动netty服务，使用新的线程启动
        new Thread(){
            @Override
            public void run() {
                nettyServer.start(1234);
            }
        }.start();
        return nettyServer;
    }

}
