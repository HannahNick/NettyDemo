package com.nick.dubborpc.customer;

import com.nick.dubborpc.api.HelloService;
import com.nick.dubborpc.netty.NettyClient;

/**
 * @author zwj
 * @date 2020/7/25
 */
public class ClientBootstrap {

    /**
     * 这里定义协议头
     */
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        NettyClient customer = new NettyClient();
        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

        //通过代理对象调用服务提供者的方法(服务)
        for (;;){
            Thread.sleep(2*1000);
            String res = service.hello("你好 dubbo");
            System.out.println("调用的结果 res=" + res);
        }

    }
}
