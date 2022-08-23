package com.hyx.loadbalance.impl;

import com.hyx.loadbalance.LoadBalance;
import com.hyx.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 一致性哈希算法.
 *
 * @author hyx
 **/

public class ConsistentHashBalance implements LoadBalance {
    
    private String name = "consistent_hash";
    
    TreeMap<Integer, String> map = new TreeMap<>();
    
    private static final Integer VIRTUAL_NODE_SIZE = 10;
    
    private static final String VIRTUAL_NODE_SUFFIX = "&&";
    
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        int hashCode = rpcRequest.hashCode();
        TreeMap<Integer, String> ring = buildConsistentHashRing(serviceAddresses);
        return locate(ring, hashCode);
    }
    
    @Override
    public String name() {
        return name;
    }
    
    /**
     * 根据请求的hash值求得其在环中应落到的位置.
     * @param ring Hash环，由buildConsistentHashRing构建
     * @param hashCode Rpc请求的hash值
     * @return 落到的服务的地址
     */
    private String locate(TreeMap<Integer, String> ring, int hashCode) {
        Map.Entry<Integer, String> locateEntry = ring.ceilingEntry(hashCode);
        if (locateEntry == null) {
            locateEntry = ring.firstEntry();
        }
        return locateEntry.getValue();
    }
    
    /**
     * 构建Hash环.
     * @param serviceAddresses 服务的远程地址组
     * @return 根据服务的远程地址组所构成的Hash环
     */
    private TreeMap<Integer, String> buildConsistentHashRing(List<String> serviceAddresses) {
        TreeMap<Integer, String> virtualNodeRing = new TreeMap<>();
        for (String address : serviceAddresses) {
            for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
                virtualNodeRing.put((address + VIRTUAL_NODE_SUFFIX + i).hashCode(), address);
            }
        }
        return virtualNodeRing;
    }
}
