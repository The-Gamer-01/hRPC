package com.hyx.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className RpcRequest
 * @description RPC请求
 * @date 2022/4/5 0:08
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 请求编号
     */
    private String requestId;

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数值数组
     */
    private Object[] parameters;

    /**
     * 参数类数组
     */
    private Class<?>[] paramTypes;

    /**
     * 接口版本
     */
    private String version;

    /**
     * 接口包
     */
    private String group;

    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup() + this.getVersion();
    }
}
