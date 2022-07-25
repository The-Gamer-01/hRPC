package com.hyx.serialize;

import com.hyx.extension.SPI;

/**
 * 序列化接口.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/6 10:37
 **/

@SPI
public interface Serializer {
    /**
     * 序列化方法.
     * @param obj 需要序列化的对象
     * @return 序列化后的字节数组
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化方法.
     * @param bytes 序列化后的字节数组
     * @param clazz 反序列化的目标类
     * @param <T> 类的类型
     * @return 反序列化后的对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
