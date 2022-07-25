package com.hyx.remoting.transport.client;

import com.hyx.remoting.dto.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 未压缩的请求.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/10 15:53
 **/

public class UnprocessedRequests {

    private static final Map<String, CompletableFuture<RpcResponse<Object>>> UNPROCESSED_PESPONSE_FUTURES = new ConcurrentHashMap<>();

    public void put(String requestId, CompletableFuture<RpcResponse<Object>> future) {
        UNPROCESSED_PESPONSE_FUTURES.put(requestId, future);
    }
    
    /**
     * 执行响应.
     * @param rpcResponse RPC响应
     */
    public void complete(RpcResponse<Object> rpcResponse) {
        CompletableFuture<RpcResponse<Object>> future = UNPROCESSED_PESPONSE_FUTURES.remove(rpcResponse.getRequestId());
        if (null != future) {
            future.complete(rpcResponse);
        } else {
            throw new IllegalStateException();
        }
    }
}
