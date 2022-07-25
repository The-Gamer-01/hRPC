package com.hyx;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.TextFormat;
import com.hyx.proto.HelloProto;

import java.util.Arrays;

/**
 * Main方法测试.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/24 11:37
 **/

public class TestMain {
    public static void main(String[] args) {
        HelloProto.Hello.Builder hello = HelloProto.Hello.newBuilder();
        hello.setMessage("test")
                .setDescription(" 测试")
                .build();
        HelloProto.Hello build = hello.build();
        byte[] bytes = build.toByteArray();
        System.out.println("protobuf数据bytes[]:" + Arrays.toString(bytes));
        System.out.println("protobuf序列化大小："  + bytes.length);

        HelloProto.Hello hello1 = null;
        String jsonObject = null;
        try {
            hello1 = HelloProto.Hello.parseFrom(bytes);
            System.out.println("反序列化：\n" + hello1.toString());
            System.out.println("中文反序列化：\n" + printToUnicodeString(hello1));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public static String printToUnicodeString(MessageOrBuilder message) {
        return TextFormat.printer().escapingNonAscii(false).printToString(message);
    }
}
