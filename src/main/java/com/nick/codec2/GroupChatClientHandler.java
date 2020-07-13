package com.nick.codec2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @author zwj
 * @date 2020/5/4
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s.trim());

    }

    /**
     * 通道就绪就会触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        //发送一个Student对象
        if (0==random){
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("白客 王大锤").build())
                    .build();
        }else {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(20).setName("王尼玛").build())
                    .build();
        }
        ctx.writeAndFlush(myMessage);
    }
}
