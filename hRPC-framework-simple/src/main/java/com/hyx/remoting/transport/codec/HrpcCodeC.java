package com.hyx.remoting.transport.codec;

import com.hyx.enums.SerializationTypeEnum;
import com.hyx.extension.ExtensionLoader;
import com.hyx.remoting.constants.RpcConstants;
import com.hyx.remoting.dto.RpcMessage;
import com.hyx.serialize.Serializer;
import com.hyx.utils.BitUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hyx
 **/

@Slf4j
public class HrpcCodeC implements Codec {
    
    public static final String NAME = "hrpc";
    
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    
    private byte getSignByte(byte codec, byte compressCode, byte eventType, byte heartBeat) {
        byte signByte = 0x0;
        signByte = BitUtils.setBit(signByte, 0, 4, codec, 4, 4);
        signByte = BitUtils.setBit(signByte, 4, 2, compressCode, 6, 2);
        signByte = BitUtils.setBit(signByte, 6, 1, eventType, 7, 1);
        signByte = BitUtils.setBit(signByte, 7, 1, heartBeat, 7, 1);
        return signByte;
    }
    
    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf out) {
        try {
            //写入魔数
            out.writeByte(RpcConstants.MAGIC_NUMBER);
        
            //写入协议版本
            //获取序列化方法
            byte codec = rpcMessage.getCodec();
        
            //获取压缩类型
            byte compressCode = rpcMessage.getCompress();
            //消息类型
            byte eventType = rpcMessage.getMessageType();
            byte heartBeatType = 0;
            out.writeByte(getSignByte(codec, compressCode, eventType, heartBeatType));
        
            //协议版本
            byte version = RpcConstants.VERSION;
            //扩展位
            byte extendCode = 0;
            //写入协议版本与扩展位
            out.writeByte(version + extendCode);
        
            //写入请求编号
            out.writeInt(ATOMIC_INTEGER.getAndIncrement());
            //写入数据长度
            String codecName = SerializationTypeEnum.getName(codec);
            Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class)
                    .getExtension(codecName);
            byte[] bodyBytes = serializer.serialize(rpcMessage.getData());
            out.writeInt(bodyBytes.length);
            //写入数据
            out.writeBytes(bodyBytes);
        } catch (Exception e) {
            log.error("对请求编码失败！", e);
        }
    }
    
    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        return null;
    }
}
