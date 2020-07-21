package com.nick.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zwj
 * @date 2020/7/19
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个出站handler对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());
        //加入一个入站handler对数据进行解码器
        pipeline.addLast(new MyByteToLongDecoder2());
        //加入一个一个自定义handler，业务处理
        pipeline.addLast(new MyClientHandler());
    }
}
