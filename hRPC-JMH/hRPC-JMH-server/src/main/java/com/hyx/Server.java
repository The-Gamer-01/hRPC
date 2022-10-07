package com.hyx;

import com.hyx.config.RpcServiceConfig;
import com.hyx.remoting.transport.server.netty.NettyServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * JMH测试服务端.
 *
 * @author hyx
 **/

public class Server {
    
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Server.class);
        NettyServer nettyServer = (NettyServer) applicationContext.getBean("nettyServer");
//        HelloService helloService = new HelloServiceImpl();
//        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
//                .group("test1")
//                .version("version1")
//                .service(helloService)
//                .build();
//        nettyServer.registerService(rpcServiceConfig);
//        try {
//            nettyServer.start();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
