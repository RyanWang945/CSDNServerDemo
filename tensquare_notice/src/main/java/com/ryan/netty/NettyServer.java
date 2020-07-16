package com.ryan.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author Ryan
 * @date 2020/7/16 12:30
 */
public class NettyServer {

    public void start(int port){
        System.out.println("准备启动netty");
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        //处理新连接
        EventLoopGroup boos=new NioEventLoopGroup();
        //处理业务逻辑，读写
        EventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(worker, worker)
                //.localAddress(port)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        //请求消息解码器
                        channel.pipeline().addLast(new HttpServerCodec());
                        //将多个消息转换为单一的request或者response对象
                        channel.pipeline().addLast(new HttpObjectAggregator(65536));
                        //处理WebSocket的消息事件
                        channel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));

                        //创建自己的websocket处理器，用来编写业务逻辑
                        MyWebSocketHandler myWebSocketHandler=new MyWebSocketHandler();
                        channel.pipeline().addLast(myWebSocketHandler);
                    }
                }).bind(port);
    }
}
