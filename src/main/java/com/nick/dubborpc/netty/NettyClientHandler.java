package com.nick.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author zwj
 * @date 2020/7/25
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<Object> {

    /**
     * 上下文
     */
    private ChannelHandlerContext context;
    /**
     * 返回的结果
     */
    private String result;
    /**
     * 客户端调用方法时传入的参数
     */
    private String para;

    /**
     * 1.这个方法是第一个被调用的
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive 被调用");
        //因为我们在其他方法会使用到ctx
        context = ctx;

    }


    /**
     * 4.
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead 被调用");
        result = msg.toString();
        //唤醒等待的线程
        notify();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught 被调用");
        ctx.close();
    }

    /**
     * 3.被代理对象调用,发送数据给服务器->wait 等待被唤醒 ->返回结果 ->4.  ->3.
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call1 被调用");
        context.writeAndFlush(para);
        //进行wait
        //等待channelRed 方法获取到服务器的结果后，唤醒
        wait();
        System.out.println("call2 被调用");
        return result;
    }

    /**
     * 2.
     * @param para
     */
    void setPara(String para){
        System.out.println("setPara 被调用");
        this.para = para;
    }
}
