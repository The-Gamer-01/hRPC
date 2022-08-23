package com.hyx.loadbalance;

import com.hyx.extension.SPI;
import com.hyx.remoting.dto.RpcRequest;

import java.util.List;

/**
 * 负载均衡接口.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/23 19:59
 **/

@SPI
public interface LoadBalance {
    /**
     * 选取地址.
     * @param serviceAddresses 地址列表
     * @param rpcRequest rpc请求
     * @return 选取后的地址
     */
    String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest);
    
    /**
     * 获取负载均衡算法名.
     * @return 负载均衡算法名.
     */
    String name();
}
