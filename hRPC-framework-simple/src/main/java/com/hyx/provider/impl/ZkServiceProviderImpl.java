package com.hyx.provider.impl;

import com.hyx.config.RpcServiceConfig;
import com.hyx.enums.RpcErrorMessageEnum;
import com.hyx.exception.RpcException;
import com.hyx.extension.ExtensionLoader;
import com.hyx.provider.ServiceProvider;
import com.hyx.registry.ServiceRegistry;
import com.hyx.remoting.transport.server.netty.NettyServer;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Zookeeper相关服务提供列.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/23 19:01
 **/

@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    /**
     * serviceName -> serviceObject服务名映射服务对象.
     */
    private final Map<String, Object> serviceMap;

    /**
     * 已注册服务.
     */
    private final Set<String> registeredService;

    /**
     * 服务注册中心.
     */
    private final ServiceRegistry serviceRegistry;

    public ZkServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("zookeeper");
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getRpcServiceName();
        //服务已经在集合中
        if (registeredService.contains(rpcServiceName)) {
            return;
        }
        registeredService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("新增服务:{},接口为:{}", rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        //获取服务失败
        if (null == service) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            this.addService(rpcServiceConfig);
            serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), new InetSocketAddress(host, NettyServer.PORT));
        } catch (UnknownHostException e) {
            log.error("获取地址时异常", e);
        }
    }
    
    
}
