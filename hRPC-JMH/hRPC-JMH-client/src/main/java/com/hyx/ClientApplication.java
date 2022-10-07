package com.hyx;

import com.hyx.annotation.RpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hyx
 **/


@RpcScan(basePackage = {"com.hyx"})
@SpringBootApplication(scanBasePackages = "com.hyx")
public class ClientApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
