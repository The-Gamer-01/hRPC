package com.hyx;

import com.hyx.annotation.RpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 客户端启动方法.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/10 13:52
 **/

@RpcScan(basePackage = {"com.hyx"})
@SpringBootApplication(scanBasePackages = "com.hyx")
public class NettyClientMain {
    public static void main(String[] args) {
        SpringApplication.run(NettyClientMain.class, args);
    }
}
