package com.hyx.exception;


/**
 * @author 黄乙轩
 * @version 1.0
 * @className SerialException
 * @description 序列化异常
 * @date 2022/5/6 10:49
 **/

public class SerialException extends RuntimeException {
    public SerialException(String message) {
        super(message);
    }
}
