package com.ryan.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryan.config.ApplicationContextProvider;
import com.ryan.entity.Result;
import com.ryan.entity.StatusCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ryan
 * @date 2020/7/16 12:31
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ObjectMapper mapper=new ObjectMapper();

    //存放websocket连接，map，根据用户id存放
    public static ConcurrentHashMap<String, Channel> userChannelMap=new ConcurrentHashMap<>();

    //从spring容器中获取消息监听器容器
    SimpleMessageListenerContainer sysNoticeContainer= (SimpleMessageListenerContainer) ApplicationContextProvider.getApplicationContext()
            .getBean("sysNoticeContainer");
    //从spring容器中获取消息监听器容器
    SimpleMessageListenerContainer userNoticeContainer= (SimpleMessageListenerContainer) ApplicationContextProvider.getApplicationContext()
            .getBean("userNoticeContainer");

    RabbitTemplate rabbitTemplate= ApplicationContextProvider.getApplicationContext()
            .getBean(RabbitTemplate.class);

    //用户请求websocket服务端，执行的方法
    //第一次请求需要建立websocket连接
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //约定用户第一次请求携带的数据：{"userId"："1"}
        //获取用户请求数据并解析
        String json=msg.text();
        //解析json
        String userId = mapper.readTree(json).get("userId").asText();
        //第一次请求需要建立websocket连接
        Channel channel = userChannelMap.get(userId);
        if(channel==null){
            //获取websocket连接
            channel=ctx.channel();
            //将间接放入容器
            userChannelMap.put(userId,channel);
        }

        //只用完成新消息的提醒即可，只需要获取消息的数量
        //获取rabbitmq的消息内容并发送给用户
        ConnectionFactory connectionFactory;
        RabbitAdmin rabbitAdmin=new RabbitAdmin(rabbitTemplate);
        //拼接获取队列名称
        String queueName="article_subscribe_"+userId;
        Properties queueProperties = rabbitAdmin.getQueueProperties(queueName);
        //获取消息数量
        int noticeCount=0;

        //判断properties是否不为空
        if(queueProperties!=null){
            noticeCount=(int)queueProperties.get("QUEUE_MESSAGE_COUNT");
        }

        //-----------------
        //拼接获取队列名称
        String userQueueName="article_thumbup_"+userId;
        Properties userQueueProperties = rabbitAdmin.getQueueProperties(userQueueName);
        //获取消息数量
        int userNoticeCount=0;

        //判断properties是否不为空
        if(userQueueProperties!=null){
            userNoticeCount=(int)userQueueProperties.get("QUEUE_MESSAGE_COUNT");
        }
        //------------------------
        //封装返回的数据
        HashMap countMap=new HashMap<>();
        //订阅类消息数量
        countMap.put("sysNoticeCount",noticeCount);
        //点赞类消息数量
        countMap.put("userNoticeCount",userNoticeCount);
        Result result=new Result(true, StatusCode.OK,"查询成功");
        //把数据发送给用户
        //mapper可以双向解析
        channel.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(result)));
        //把消息从队列中清空，避免mq重复消费
        if(noticeCount>0)
            rabbitAdmin.purgeQueue(queueName,true);
        if(userNoticeCount>0)
            rabbitAdmin.purgeQueue(userQueueName,true);
        //为用户的消息通知队列注册监听器，便于用户在线时一旦有消息可以主动推送给用户
        //不需要用户请求服务器
        sysNoticeContainer.addQueueNames(queueName);
        userNoticeContainer.addQueueNames(userQueueName);
    }
}
