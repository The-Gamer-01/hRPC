package com.hyx.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RPC响应.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/5 0:08
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcResponse<T> implements Serializable {
    /**
     * 请求编号.
     */
    private String requestId;

    private T data;
    
    /**
     * RPC请求成功请求.
     * @param result 结果
     * @param requestId 请求编号
     */
    public static RpcResponse<Object> success(Object result, String requestId) {
        RpcResponse<Object> rpcResponse = new RpcResponse<>();
        rpcResponse.setData(result);
        rpcResponse.setRequestId(requestId);
        return rpcResponse;
    }
    
    /**
     * RPC请求失败请求.
     * @param result 请求结果
     * @param requestId 请求编号
     */
    public static RpcResponse<Object> fail(Object result, String requestId) {
        RpcResponse<Object> rpcResponse = RpcResponse.builder()
                .data(result)
                .requestId(requestId)
                .build();
        return rpcResponse;
    }
}
