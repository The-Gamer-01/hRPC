package com.hyx.exception;

/**
 * 序列化异常.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/6 10:49
 **/
public class SerialException extends RuntimeException {
    public SerialException(String message) {
        super(message);
    }
}
