package com.hyx.remoting;

import java.net.InetSocketAddress;
import java.net.URL;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className Endpoint
 * @description 抽象出端的概念
 * @date 2022/4/19 11:10
 **/

public interface Endpoint {
    /**
     * 获取端的url
     * @return url
     */
    URL getUrl();

    /**
     * 获取本地地址
     * @return 本地地址
     */
    InetSocketAddress getLocalAddress();
}
