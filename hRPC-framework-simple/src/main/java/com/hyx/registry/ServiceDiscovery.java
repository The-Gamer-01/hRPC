package com.hyx.registry;

import com.hyx.extension.SPI;
import com.hyx.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * 服务发现接口.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/5 0:06
 **/

@SPI
public interface ServiceDiscovery {
    /**
     * 根据服务名获取远程服务地址.
     * @param rpcRequest rpc请求
     * @return 远程服务地址
     */
    InetSocketAddress lookUpService(RpcRequest rpcRequest);
}
