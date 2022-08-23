package com.hyx.loadbalance.impl;

import com.hyx.loadbalance.LoadBalance;
import com.hyx.remoting.dto.RpcRequest;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询算法.
 * @author hyx
 **/

public class RoundLoadBalance implements LoadBalance {
    
    private String name = "round";
    
    private AtomicInteger index = new AtomicInteger(0);
    
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        return serviceAddresses.get(index.incrementAndGet() % serviceAddresses.size());
    }
    
    @Override
    public String name() {
        return name;
    }
}
