package com.hyx.registry;

import com.hyx.extension.SPI;

import java.net.InetSocketAddress;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className ServiceRegistry
 * @description 服务注册接口
 * @date 2022/4/5 0:05
 **/

@SPI
public interface ServiceRegistry {
    /**
     * 服务注册接口
     * @param serviceName 需要注册的Service的ServiceName
     * @param address 远程服务地址
     */
    void registerService(String serviceName, InetSocketAddress address);
}
