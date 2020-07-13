package com.nick.codec2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

/**
 * @author zwj
 * @date 2020/5/4
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    /**
     * 定义一个channel组，管理所有的channel
     * GlobalEventExecutor.INSTANCE是全局事件执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 表示连接建立,一旦连接,第一个被执行
     * 将当前channel 加入到channelGroup
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户假如聊天的信息推送给其他在线的客户端
        //该方法会将channelGroup中所有的channel遍历,并发送消息,我们不需要自己遍历
        channelGroup.writeAndFlush("客户端".concat(channel.remoteAddress().toString()).concat("加入聊天\n"));
        channelGroup.add(channel);
    }

    /**
     * 表示channel 处于活动状态,提示xx上线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress().toString().concat("上线了~"));
    }

    /**
     * 表示channel处于不活动状态,提示xx离线了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"离线了~");
    }

    /**
     * 断开连接，将xx客户离开信息推送给当前在线的客户
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(String.format("【客户端】%1$s 离开了",channel.remoteAddress()));
        System.out.println("channelGroup size" + channelGroup.size());
    }

    /**
     * 读取数据
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.MyMessage s) throws Exception {
        //获取到当前的channel
        Channel channel = channelHandlerContext.channel();
        //遍历channelGroup，根据不同的情况回送不同的消息
        channelGroup.forEach(ch->{
            if (channel != ch){//不是当前的channel，转发消息
                ch.writeAndFlush(String.format("【客户】%1$s 说:%2$s \n",channel.remoteAddress(),s));
            }else {//回显自己发送的消息给自己
                MyDataInfo.MyMessage.DataType dataType = s.getDataType();
                if (dataType == MyDataInfo.MyMessage.DataType.StudentType){
                    ch.writeAndFlush(String.format("【自己】发送了消息%1$s \n",s.getStudent().getName()));
                }else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType){
                    ch.writeAndFlush(String.format("【自己】发送了消息%1$s \n",s.getWorker().getName()));
                }else {
                    System.out.println("传输的类型不正确");
                }

            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
