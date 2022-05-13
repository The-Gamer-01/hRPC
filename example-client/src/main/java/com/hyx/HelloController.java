package com.hyx;

import com.hyx.annotation.RpcReference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className com.hyx.HelloController
 * @description TODO
 * @date 2022/5/10 11:06
 **/

@CrossOrigin
@ResponseBody
@RestController
public class HelloController {
    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    @RequestMapping("/test")
    public String test() throws InterruptedException {
        Integer number = new Random().nextInt();
        Thread.sleep(1000);
        return helloService.hello(new Hello(String.valueOf(number), "第" + number + "个请求"));
    }
}
