package com.nick.dubborpc.netty;

import com.nick.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zwj
 * @date 2020/7/25
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息并调用服务
        System.out.println("msg="+msg);
        //客户端在调用服务器的api时需要定义一个协议
        //比如我们要求 每次发消息时都必须以某个字符串开头"HelloService#hello#xxxxxx"
        String content = msg.toString();
        if (content.startsWith("HelloService#hello#")){
            String hello = new HelloServiceImpl().hello(content.substring(content.lastIndexOf("#") + 1));
            ctx.writeAndFlush(hello);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
