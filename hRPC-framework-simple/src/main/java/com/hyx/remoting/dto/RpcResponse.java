package com.hyx.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className RpcResponse
 * @description RPC响应
 * @date 2022/4/5 0:08
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcResponse <T> implements Serializable {
    /**
     * 请求编号
     */
    private String requestId;

    private T data;

    public static RpcResponse<Object> success(Object result, String requestId) {
        RpcResponse<Object> rpcResponse = new RpcResponse<>();
        rpcResponse.setData(result);
        rpcResponse.setRequestId(requestId);
        return rpcResponse;
    }

    public static RpcResponse<Object> fail(Object result, String requestId) {
        RpcResponse<Object> rpcResponse = RpcResponse.builder()
                .data(result)
                .requestId(requestId)
                .build();
        return rpcResponse;
    }
}
