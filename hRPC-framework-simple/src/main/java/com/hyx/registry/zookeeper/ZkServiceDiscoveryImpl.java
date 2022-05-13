package com.hyx.registry.zookeeper;

import com.hyx.enums.RpcErrorMessageEnum;
import com.hyx.exception.RpcException;
import com.hyx.loadbalance.Impl.RandomLoadBalance;
import com.hyx.loadbalance.LoadBalance;
import com.hyx.registry.ServiceDiscovery;
import com.hyx.registry.zookeeper.utils.CuratorUtils;
import com.hyx.remoting.dto.RpcRequest;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className ZkServiceDiscoveryImpl
 * @description TODO
 * @date 2022/4/23 19:04
 **/

public class ZkServiceDiscoveryImpl implements ServiceDiscovery {

    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = new RandomLoadBalance();
    }

    @Override
    public InetSocketAddress lookUpService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if(serviceUrlList == null || serviceUrlList.size() == 0) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
