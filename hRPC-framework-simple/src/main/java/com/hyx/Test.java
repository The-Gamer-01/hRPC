package com.hyx;

import com.hyx.extension.ExtensionLoader;
import com.hyx.registry.ServiceRegistry;
import com.hyx.remoting.RpcRequestTransport;
import com.hyx.remoting.constants.RpcConstants;
import com.hyx.remoting.transport.client.NettyRpcClient;
import com.hyx.serialize.Serializer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className Test
 * @description TODO
 * @date 2022/5/6 16:45
 **/

public class Test {
    public static void main(String[] args) throws IOException {
        RpcRequestTransport rpcClient = ExtensionLoader.getExtensionLoader(RpcRequestTransport.class).getExtension("netty");
//        ServiceRegistry serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("zookeeper");
//        RpcRequestTransport rpcRequestTransport = new NettyRpcClient();
    }
}
