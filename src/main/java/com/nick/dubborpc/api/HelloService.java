package com.nick.dubborpc.api;

/**
 * 这个接口是服务提供方和服务消费方都需要的
 * @author zwj
 * @date 2020/7/25
 */
public interface HelloService {
    String hello(String msg);
}
