package com.hyx.serialize.kryo;

import com.hyx.extension.ExtensionLoader;
import com.hyx.remoting.dto.RpcMessage;
import com.hyx.serialize.Serializer;
import org.junit.Test;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author hyx
 **/

public class KryoSerializerTest {
    
    @Test
    public void test() {
        Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension("kryo");
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setCodec((byte) 1);
        rpcMessage.setCompress((byte) 1);
        rpcMessage.setMessageType((byte) 1);
        rpcMessage.setRequestId(1);
        List<String> list = Arrays.asList("1", "2", "3");
        rpcMessage.setData(list);
        System.out.println(rpcMessage);
        byte[] serialize = serializer.serialize(rpcMessage);
        for(byte b : serialize) {
            System.out.print(b + " ");
        }
        System.out.println();
        RpcMessage deserialize = serializer.deserialize(serialize, RpcMessage.class);
        System.out.println(deserialize);
    }
}
