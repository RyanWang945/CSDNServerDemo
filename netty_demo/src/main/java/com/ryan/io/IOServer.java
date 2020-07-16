package com.ryan.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Ryan
 * @date 2020/7/16 9:18
 */
//传统io编程服务端
public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(8000);
        while(true){
            //使用阻塞的方式获取新的连接
            Socket socket=serverSocket.accept();
            //每个客户端连接时会创建一个新线程来处理
            new Thread(){
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    byte[] data=new byte[1024];
                    try {
                        InputStream inputStream = socket.getInputStream();
                        while(true){
                            int len;
                            //使用字节流读取数据
                            while((len=inputStream.read(data))!=-1){
                                System.out.println("线程"+name+":"+new String(data,0,len));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }
}
