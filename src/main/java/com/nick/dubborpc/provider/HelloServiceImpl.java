package com.nick.dubborpc.provider;

import com.nick.dubborpc.api.HelloService;

/**
 * @author zwj
 * @date 2020/7/25
 */
public class HelloServiceImpl implements HelloService {
    /**
     * 当有消费方调用该方法时，就返回一个结果
     * @param msg
     * @return
     */
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息="+msg);
        //根据mes，返回不同的结果
        if (msg!=null){
            return "你好客户端，我已经收到你的消息【"+msg+"】";
        }

        return "你好客户端，我已经收到你的消息";
    }
}
