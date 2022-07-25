package com.hyx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 序列化枚举类.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/6 16:16
 **/
@Getter
@AllArgsConstructor
public enum  SerializationTypeEnum {
    /**
     * KYRO序列化方式.
     */
    KYRO((byte) 0x01, "kryo"),
    /**
     * PROTOSTUFF序列化方式.
     */
    PROTOSTUFF((byte) 0x02, "protostuff"),
    /**
     * HESSIAN序列化方式.
     */
    HESSIAN((byte) 0x03, "hessian");

    /**
     * 序列化方式编号.
     */
    private final byte code;
    /**
     * 序列化名称.
     */
    private final String name;

    /**
     * 根据序列化编号获取序列化方式名字.
     * @param code 序列化编号
     * @return 序列化方式名字
     */
    public static String getName(byte code) {
        for (SerializationTypeEnum type : SerializationTypeEnum.values()) {
            if (type.getCode() == code) {
                return type.name;
            }
        }
        return null;
    }
}
