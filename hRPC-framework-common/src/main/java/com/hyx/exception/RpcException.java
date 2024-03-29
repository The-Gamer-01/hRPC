package com.hyx.exception;

import com.hyx.enums.RpcErrorMessageEnum;

/**
 * RPC异常.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/23 20:07
 **/
public class RpcException extends RuntimeException {
    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }
}
