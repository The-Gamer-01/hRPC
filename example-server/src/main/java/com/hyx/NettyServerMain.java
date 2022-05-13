package com.hyx;

import com.hyx.annotation.RpcScan;
import com.hyx.annotation.RpcService;
import com.hyx.config.RpcServiceConfig;
import com.hyx.remoting.transport.server.netty.NettyServer;
import com.hyx.serviceImpl.HelloServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className NettyServerMain
 * @description TODO
 * @date 2022/5/10 11:17
 **/

@RpcScan(basePackage = {"com.hyx"})
public class NettyServerMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyServer nettyServer = (NettyServer) applicationContext.getBean("nettyServer");
        HelloService helloService = new HelloServiceImpl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test1")
                .version("version1")
                .service(helloService)
                .build();
        nettyServer.registerService(rpcServiceConfig);
        nettyServer.start();
    }
}
