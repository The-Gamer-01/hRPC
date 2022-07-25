package com.hyx.serialize.protobuf;

import com.hyx.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className ProtostuffSerializer
 * @description protobuf序列化方式
 * @date 2022/5/23 18:28
 **/

@Slf4j
public class ProtobufSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return null;
    }
}
