package com.hyx.remoting.handler;

import com.hyx.exception.RpcException;
import com.hyx.factory.SingletonFactory;
import com.hyx.provider.ServiceProvider;
import com.hyx.provider.impl.ZkServiceProviderImpl;
import com.hyx.remoting.dto.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className RpcRequestHandler
 * @description RpcRequest处理类
 * @date 2022/4/22 13:16
 **/

public class RpcRequestHandler {

    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    public Object handler(RpcRequest rpcRequest) {
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     * 获取方法执行结果
     * @param rpcRequest rpcRequest
     * @param service service对象
     * @return 执行结果
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }
}
