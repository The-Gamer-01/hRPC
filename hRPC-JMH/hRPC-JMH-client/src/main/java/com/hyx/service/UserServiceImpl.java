package com.hyx.service;

import com.hyx.Hello;
import com.hyx.HelloService;
import com.hyx.annotation.RpcReference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hyx
 **/

@Component
public class UserServiceImpl implements UserService {
    
    private HelloService helloService;
    
    private AtomicLong number = new AtomicLong(new Random().nextLong());
    
    @Override
    @RequestMapping("/test")
    public String test() throws InterruptedException {
        System.out.println("开始test方法");
        return helloService.hello(new Hello(String.valueOf(number), "第" + number.getAndIncrement() + "个请求"));
    }
}
