package com.hyx.service.impl;

import com.hyx.Hello;
import com.hyx.HelloService;
import com.hyx.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * HelloService实现类.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/10 11:15
 **/

@Slf4j
@Component
public class HelloServiceImpl implements HelloService {

    @Override
    @RpcReference(group = "test1", version = "version1")
    public String hello(Hello hello) {
        return "Hello description is " + hello.getDescription();
    }
}
