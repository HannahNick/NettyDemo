package com.nick.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;


/**
 * @author zwj
 * @date 2020/7/21
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        //将buffer转成字符串
        String s = new String(bytes, Charset.forName("utf-8"));
        System.out.println("服务器接收到数据"+s);
        System.out.println("服务器接受到消息量"+(++this.count));

        //服务器回送数据给客户端，回送一个随机id,
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString()+" ", Charset.forName("utf-8"));
        ctx.writeAndFlush(responseByteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
