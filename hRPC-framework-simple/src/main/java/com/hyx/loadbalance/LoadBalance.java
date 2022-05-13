package com.hyx.loadbalance;

import com.hyx.extension.SPI;
import com.hyx.remoting.dto.RpcRequest;

import java.util.List;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className LoadBalance
 * @description 负载均衡接口
 * @date 2022/4/23 19:59
 **/

@SPI
public interface LoadBalance {
    /**
     * 选取地址
     * @param serviceAddresses 地址列表
     * @param rpcRequest rpc请求
     * @return 选取后的地址
     */
    String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest);
}
