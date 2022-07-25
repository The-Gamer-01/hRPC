package com.hyx.registry.zookeeper;

import com.hyx.registry.ServiceRegistry;
import com.hyx.registry.zookeeper.utils.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;

/**
 * Zookeeper注册中心获取客户端.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/10 10:34
 **/

public class ZkServiceRegistryImpl implements ServiceRegistry {
    @Override
    public void registerService(String serviceName, InetSocketAddress address) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + serviceName + address.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}
