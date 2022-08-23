package com.hyx.loadbalance.impl;

import com.hyx.annotation.RpcReference;
import com.hyx.loadbalance.LoadBalance;
import com.hyx.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡算法, 此处完全参考dubbo.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/23 20:02
 **/

public class RandomLoadBalance implements LoadBalance {
    
    private String name = "random";
    
    private final Random random = new Random();
    
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        int length = serviceAddresses.size();
        int totalWeight = 0;
        boolean sameWeight = true;
        for(int i = 0; i < length; i++) {
            int weight = getWeight(serviceAddresses.get(i), rpcRequest);
            totalWeight += weight;
            if(sameWeight && i > 0
                && weight != getWeight(serviceAddresses.get(i - 1), rpcRequest)) {
                sameWeight = false;
            }
        }
        if(totalWeight > 0 && ! sameWeight) {
            int offset = random.nextInt(totalWeight);
            for(int i = 0; i < length; i++) {
                offset -= getWeight(serviceAddresses.get(i), rpcRequest);
                if(offset < 0) {
                    return serviceAddresses.get(i);
                }
            }
        }
        return serviceAddresses.get(random.nextInt(length));
    }
    
    @Override
    public String name() {
        return name;
    }
    
    protected int getWeight(String address, RpcRequest request) {
        return request.getWeight();
    }
}
