package com.hyx.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.hyx.exception.SerialException;
import com.hyx.remoting.dto.RpcRequest;
import com.hyx.remoting.dto.RpcResponse;
import com.hyx.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className KryoSerializer
 * @description Kryo序列化方法
 * @date 2022/5/6 10:40
 **/

@Slf4j
public class KryoSerializer implements Serializer {
    /**
     * Kryo线程不安全，使用ThreadLocal保证线程安全
     */
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcRequest.class);
        kryo.register(RpcResponse.class);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output, obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            throw new SerialException("序列化失败");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)){
            Kryo kryo = kryoThreadLocal.get();
            Object obj = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(obj);
        } catch (Exception e) {
            throw new SerialException("反序列化失败");
        }
    }

    public static void main(String[] args) {
        Serializer serializer = new KryoSerializer();
        byte[] serialize = new byte[]{1, 3, 1, 72, 101, 108, 108, 111, 32, 100, 101, 115, 99, 114, 105, 112, 116, 105, 111, 110, 32, 105, 115, -78, 1, 99, 102, 54, 51, 99, 101, 56, 56, 45, 49, 98, 55, 50, 45, 52, 56, 56, 98, 45, 97, 51, 100, 51, 45, 102, 54, 98, 97, 55, 52, 57, 50, 100, 99, 48, -72};
        RpcResponse deserialize = serializer.deserialize(serialize, RpcResponse.class);
        System.out.println(deserialize);
    }
}
