package com.hyx.utils;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className BitUtils
 * @description 位运算工具类
 * @date 2022/5/6 1:02
 **/

public class BitUtils {

    /**
     * 值为0的byte
     */
    private static final byte ZERO = 0x0;

    /**
     * 值为1的byte
     */
    private static final byte ONE = 0x1;

    /**
     * byte长度
     */
    private static final byte BYTE_LENGTH = 8;

    /**
     * 将byte数据的某一个位置设置为0或者1
     * @param targetNum 原数据
     * @param bitValue 位值
     * @param pos 需要修改的位置
     * @return 修改后的值
     */
    public static byte setBit(int targetNum, byte bitValue, int pos) {
        if(bitValue == ZERO) {
            targetNum &= ~(ONE << (BYTE_LENGTH - 1 - pos));
        } else if (bitValue == ONE) {
            targetNum |= (ONE << (BYTE_LENGTH - 1 - pos));
        }
        return (byte) targetNum;
    }

    /**
     * 将一个byte某个位范围的位数设置为另外一个byte某个位范围的位数
     * @param targetNum 目标byte
     * @param targetStart 位开始位置
     * @param targetLen 位长度
     * @param bitValue 需要修改的byte
     * @param bitStart 需要修改的byte开始位置位
     * @param bitLen 需要修改的byte位长度
     * @return
     */
    public static byte setBit(byte targetNum, int targetStart, int targetLen,
                              byte bitValue, int bitStart, int bitLen) {
        if(targetLen != bitLen) {
            throw new RuntimeException("长度不一致");
        }
        for(int i = targetStart, j = bitStart; i < targetStart + targetLen && j < bitStart + bitLen; i++, j++) {
            targetNum = setBit(targetNum, getBit(bitValue, j, 1), i);
        }
        return targetNum;
    }

    public static void main(String[] args) {
        byte sign = 0x0;
        byte bitValue = (byte) 255;
        print(bitValue);
        sign = setBit(sign, 0, 4, bitValue, 3, 4);
        print(sign);
    }

    /**
     *
     * @param targetNum
     * @param targetStart
     * @param targetLen
     * @return
     */
    public static byte getBit(byte targetNum, int targetStart, int targetLen) {
        byte fullTargetBitValue = getFullValue(targetStart, targetLen);
        targetNum = (byte) (targetNum & fullTargetBitValue);
        return (byte) (targetNum >> (BYTE_LENGTH - targetLen - targetStart));
    }

    /**
     * 移动位数
     * @param bitValue 需要移动位数的byte值
     * @param start 开始的位置
     * @param length 需要保留的长度
     * @return
     */
    private static byte moveBit(byte bitValue, int start, int length) {
        return (byte) (bitValue << (BYTE_LENGTH - start - length));
    }

    /**
     * 获取所有位数为1的byte
     * @param start 开始的位置
     * @param length 需要的长度
     * @return
     */
    private static byte getFullValue(int start, int length) {
        byte bit = ZERO;
        for(int i = 0; i < length; i++) {
            bit = (byte) (bit << ONE | ONE);
        }
        bit = moveBit(bit, start, length);
        return bit;
    }

    /**
     * 测试方法，留作纪念
     * @param bit 需要打印的byte
     */
    public static void print(byte bit) {
        byte[] bits = new byte[BYTE_LENGTH];
        for(int i = BYTE_LENGTH - 1; i >= 0; i--) {
            bits[i] = (byte) (bit & ONE);
            bit = (byte) (bit >> ONE);
        }
        for (byte b : bits) {
            System.out.print(b);
        }
        System.out.println();
    }

}
