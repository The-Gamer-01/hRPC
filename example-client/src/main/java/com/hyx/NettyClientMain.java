package com.hyx;

import com.hyx.annotation.RpcScan;
import com.hyx.controller.HelloController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className NettyClientMain
 * @description TODO
 * @date 2022/5/10 13:52
 **/

@RpcScan(basePackage = {"com.hyx"})
@SpringBootApplication(scanBasePackages = "com.hyx")
public class NettyClientMain {
    public static void main(String[] args) throws InterruptedException {
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
//        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
//        for(int i = 0; i < 10; i++) {
//            helloController.test();
//        }
        SpringApplication.run(NettyClientMain.class, args);
    }
}
