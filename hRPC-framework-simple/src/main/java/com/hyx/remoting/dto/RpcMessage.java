package com.hyx.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 真实传输数据.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/19 0:33
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcMessage<T> implements Serializable {
    /**
     * 序列化方式.
     */
    private byte codec;

    /**
     * 请求id.
     */
    private int requestId;

    /**
     * 具体数据.
     */
    private T data;

    /**
     * 压缩方式.
     */
    private byte compress;

    /**
     * 请求类型.
     */
    private byte messageType;
}