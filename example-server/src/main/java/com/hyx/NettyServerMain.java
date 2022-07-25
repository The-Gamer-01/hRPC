package com.hyx;

import com.hyx.annotation.RpcScan;
import com.hyx.config.RpcServiceConfig;
import com.hyx.remoting.transport.server.netty.NettyServer;
import com.hyx.service.impl.HelloServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Netty服务端启动类.
 * @author 黄乙轩
 * @version 1.0
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
