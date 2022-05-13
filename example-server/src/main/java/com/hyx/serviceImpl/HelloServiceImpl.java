package com.hyx.serviceImpl;

import com.hyx.Hello;
import com.hyx.HelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className HelloServiceImpl
 * @description TODO
 * @date 2022/5/10 11:15
 **/

@Slf4j
public class HelloServiceImpl implements HelloService {
    static {
        log.info("HelloServiceImpl被创建");
    }

    @Override
    public String hello(Hello hello) {
        String result = "Hello description is " + hello.getDescription();
        return result;
    }
}
