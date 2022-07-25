package com.hyx.remoting;

import com.hyx.extension.SPI;
import com.hyx.remoting.dto.RpcRequest;

/**
 * 发送请求接口.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/19 18:46
 **/

@SPI
public interface RpcRequestTransport {
    /**
     * 发送RPC请求并且获取结果.
     * @param rpcRequest RPC请求
     * @return 响应
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
