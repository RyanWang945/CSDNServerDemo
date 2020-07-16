package com.ryan.config;

import com.ryan.listener.SysNoticeListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ryan
 * @date 2020/7/16 12:32
 */
@Configuration
public class RabbitConfig {


    @Bean("sysNoticeContainer")
    public SimpleMessageListenerContainer create(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);
        //使用channel监听
        container.setExposeListenerChannel(true);
        //设置自己的监听器
        container.setMessageListener(new SysNoticeListener());
        return container;
    }

    @Bean("userNoticeContainer")
    public SimpleMessageListenerContainer createUser(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer(connectionFactory);
        //使用channel监听
        container.setExposeListenerChannel(true);
        //设置自己的监听器
        container.setMessageListener(new SysNoticeListener());
        return container;
    }

}
