package com.ryan.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.ryan.entity.Result;
import com.ryan.entity.StatusCode;
import com.ryan.netty.MyWebSocketHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.HashMap;

/**
 * @author Ryan
 * @date 2020/7/16 12:32
 */
public class SysNoticeListener implements ChannelAwareMessageListener {

    private static  ObjectMapper mapper=new ObjectMapper();
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //获取用户id，通过队列名称获取
        String queueName = message.getMessageProperties().getConsumerQueue();
        String userId = queueName.substring(queueName.lastIndexOf("_" )+ 1);

        io.netty.channel.Channel wsChannel = MyWebSocketHandler.userChannelMap.get(userId);

        //判断用户是否在线
        if(wsChannel!=null){
            //如果连接部位空，表示用户在线
            //封装返回数据
            HashMap countMap=new HashMap();
            countMap.put("sysNoticeCount",1);
            Result result=new Result(true, StatusCode.OK,"查询成功",countMap);
            //把数据通过websocket主动推送给用户
            wsChannel.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(result)));



        }
    }
}
