package com.hyx.loadbalance.impl;

import com.hyx.loadbalance.LoadBalance;
import com.hyx.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡算法.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/23 20:02
 **/

public class RandomLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        return serviceAddresses.get(new Random().nextInt(serviceAddresses.size()));
    }
}
