package com.hyx.remoting.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className RpcConstants
 * @description RPC常量
 * @date 2022/4/21 17:03
 **/

public class RpcConstants {
    /**
     * 魔数，检验是否RpcMessage
     */
    public static final byte MAGIC_NUMBER = 0x51;

    /**
     * 默认编码
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 协议版本
     */
    public static final byte VERSION = 1;

    /**
     * 最大帧长度
     */
    public static final int MAX_FRAME_LENGTH = 8 * 1024 * 1024 ;
    /**
     * 请求事件编号
     */
    public static final byte REQUEST_EVENT = 0;

    /**
     * 响应事件编号
     */
    public static final byte RESPONSE_EVENT = 1;

    /**
     * GZIP压缩方式
     */
    public static final byte GZIP = 0x1;
}
