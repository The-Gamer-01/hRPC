package com.hyx.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hyx.Hello;
import com.hyx.HelloService;
import com.hyx.annotation.RpcReference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className com.hyx.controller.HelloController
 * @description TODO
 * @date 2022/5/10 11:06
 **/

@CrossOrigin
@ResponseBody
@RestController
public class HelloController {
    @Reference(version = "version1")
//    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    private AtomicLong number = new AtomicLong(new Random().nextLong());
    
    @RequestMapping("/test")
    public String test() throws InterruptedException {
        return helloService.hello(new Hello(String.valueOf(number), "第" + number.getAndIncrement() + "个请求"));
    }
}
