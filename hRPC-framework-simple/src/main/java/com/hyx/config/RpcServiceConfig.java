package com.hyx.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * RpcServiceConfig用以标志version，group，service.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/9 21:51
 **/

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RpcServiceConfig<T> {
    /**
     * 服务版本.
     */
    private String version = "";

    /**
     * 服务实现类组.
     */
    private String group = "";

    /**
     * 目标服务对象.
     */
    private T service;

    /**
     * 获取注册中心中的服务名称.
     * @return 服务名称
     */
    public String getRpcServiceName() {
        return this.getServiceName() + "/" + this.getGroup() + "/" + this.getVersion();
    }

    /**
     * 获取服务对象的规范名称.
     * @return 服务对象的规范名称
     */
    public String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }
}
