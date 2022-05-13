package com.hyx.provider;

import com.hyx.config.RpcServiceConfig;
import com.hyx.extension.SPI;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className ServiceProvider
 * @description 服务提供接口
 * @date 2022/4/23 18:58
 **/

@SPI
public interface ServiceProvider {

    /**
     * 新增服务
     * @param rpcServiceConfig 服务对象类
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * 根据rpcServiceName获取服务类
     * @param rpcServiceName rpc service名称
     * @return service 对象
     */
    Object getService(String rpcServiceName);

    /**
     * 发布服务对象
     * @param rpcServiceConfig 服务对象类
     */
    void publishService(RpcServiceConfig rpcServiceConfig);
}
