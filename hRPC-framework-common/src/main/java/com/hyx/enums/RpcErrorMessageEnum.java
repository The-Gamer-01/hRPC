package com.hyx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * RpcErrorMessage枚举类.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/23 20:09
 **/
@AllArgsConstructor
@Getter
@ToString
public enum  RpcErrorMessageEnum {
    SERVICE_CAN_NOT_BE_FOUND("没有找到指定的服务");
    private final String message;
}
