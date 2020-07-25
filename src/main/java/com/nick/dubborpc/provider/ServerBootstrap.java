package com.nick.dubborpc.provider;

import com.nick.dubborpc.netty.NettyServer;

/**
 * 会启动一个服务提供者，就是NettyServer
 * @author zwj
 * @date 2020/7/25
 */
public class ServerBootstrap {
    public static void main(String[] args) {

        NettyServer.startServer("127.0.0.1",7000);
    }
}
