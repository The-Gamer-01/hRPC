package com.hyx.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hyx.Hello;
import com.hyx.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className HelloServiceImpl
 * @description TODO
 * @date 2022/5/10 11:15
 **/

@Slf4j
@Component
@Service(version = "version1")
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(Hello hello) {
        String result = "Hello description is " + hello.getDescription();
        return result;
    }
}
