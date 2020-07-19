package com.nick.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zwj
 * @date 2020/7/19
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * 该方法会根据接收到的数据，被调用多次，直到确定没有新的元素被添加到list,或者是ByteBuf没有更多可读字节为止
     * 如果list out不为空，就会将list的内容传递给下一个channelInBoundHandler处理，该方法也会被调用多次
     *
     * @param ctx 上下文
     * @param in 入站的byteBuf
     * @param out 将解码后的数据传到下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder decode 被调用");
        //因为long是8个字节,需要判断有8个字节才能读取一个long
        if (in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}
