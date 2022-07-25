package com.hyx.remoting.transport.client;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Channel提供者.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/10 16:02
 **/

public class ChannelProvider {
    private final Map<String, Channel> channelMap;

    public ChannelProvider() {
        channelMap = new ConcurrentHashMap<>();
    }
    
    /**
     * 获取管道.
     * @param inetSocketAddress 绑定地址
     */
    public Channel get(InetSocketAddress inetSocketAddress) {
        String key = inetSocketAddress.toString();
        if (channelMap.containsKey(key)) {
            Channel channel = channelMap.get(key);
            if (channel != null && channel.isActive()) {
                return channel;
            } else {
                channelMap.remove(key);
            }
        }
        return null;
    }

    public void set(InetSocketAddress inetSocketAddress, Channel channel) {
        String key = inetSocketAddress.toString();
        channelMap.put(key, channel);
    }

    public void remove(InetSocketAddress inetSocketAddress) {
        String key = inetSocketAddress.toString();
        channelMap.remove(key);
    }
}
