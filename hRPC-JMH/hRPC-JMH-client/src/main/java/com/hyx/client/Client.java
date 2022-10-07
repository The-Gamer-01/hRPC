package com.hyx.client;

import com.hyx.ClientApplication;
import com.hyx.annotation.RpcScan;
import com.hyx.service.UserService;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * JMH客户端.
 *
 * @author hyx
 **/

@State(Scope.Benchmark)
public class Client {
    
    public static final int CONCURRENCY = 32;
    
    private UserService userService;

    private ConfigurableApplicationContext context;
    
    @Setup
    public void init() {
        this.context = SpringApplication.run(ClientApplication.class);
        this.userService = context.getBean(UserService.class);
    }
    
    @TearDown
    public void down() {
        context.close();
    }
    
    @Benchmark
    public void test() throws InterruptedException {
        userService.test();
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Client.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
