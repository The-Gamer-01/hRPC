package com.hyx.remoting.transport.codec;

import com.hyx.enums.SerializationTypeEnum;
import com.hyx.exception.SerialException;
import com.hyx.extension.ExtensionLoader;
import com.hyx.remoting.constants.RpcConstants;
import com.hyx.remoting.dto.RpcMessage;
import com.hyx.remoting.dto.RpcRequest;
import com.hyx.remoting.dto.RpcResponse;
import com.hyx.serialize.Serializer;
import com.hyx.utils.BitUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * RpcMessageDecoder解码器.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/20 15:11
 **/

@Slf4j
public class RpcMessageDecoder extends LengthFieldBasedFrameDecoder {
    
    public RpcMessageDecoder() {
        this(RpcConstants.MAX_FRAME_LENGTH,
                7, 4,
                0, 0);
    }

    public RpcMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                             int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decoded = super.decode(ctx, in);
        if (decoded instanceof ByteBuf) {
            ByteBuf frame = (ByteBuf) decoded;
            try {
                return decodeFrame(frame);
            } catch (Exception e) {
                throw e;
            } finally {
                frame.release();
            }
        }
        return decoded;
    }

    private Object decodeFrame(ByteBuf in) {
        //检查魔数
        checkMagicNumber(in);
        //获取标志位字节
        byte signByte = in.readByte();
        byte msgType = getMessageType(signByte);
        //检验扩展位
        checkExtend(in);
        //获取请求编号
        final int requestId = in.readInt();
        //获取数据长度
        int bodyLength = in.readInt();
        //读取数据
        byte[] body = new byte[bodyLength];
        in.readBytes(body);
        Serializer serializer = getSerializer(signByte);
        RpcMessage rpcMessage = null;
        if (msgType == RpcConstants.REQUEST_EVENT) {
            RpcRequest rpcRequest = serializer.deserialize(body, RpcRequest.class);
            rpcMessage = new RpcMessage<RpcRequest>();
            rpcMessage.setData(rpcRequest);
            rpcMessage.setMessageType(RpcConstants.REQUEST_EVENT);
        } else if (msgType == RpcConstants.RESPONSE_EVENT) {
            RpcResponse rpcResponse = serializer.deserialize(body, RpcResponse.class);
            rpcMessage = new RpcMessage<RpcResponse>();
            rpcMessage.setData(rpcResponse);
            rpcMessage.setMessageType(RpcConstants.RESPONSE_EVENT);
        } else {
            log.error("错误！");
            log.info("signByte:{}", signByte);
            log.info("msgType:{}", msgType);
        }
        rpcMessage.setCodec(getCodec(signByte));
        rpcMessage.setRequestId(requestId);
        return rpcMessage;
    }

    /**
     * 检查魔数判断报文格式是否正确.
     * @param in 缓冲流
     */
    private void checkMagicNumber(ByteBuf in) {
        byte magicNumber = in.readByte();
        if (magicNumber != RpcConstants.MAGIC_NUMBER) {
            throw new IllegalStateException("魔数错误：:" + magicNumber);
        }
    }

    /**
     * 检查扩展位.
     * @param in 缓冲流
     */
    private void checkExtend(ByteBuf in) {
        byte extendCode = in.readByte();
    }

    private Serializer getSerializer(byte signByte) {
        byte codec = getCodec(signByte);
        String codecName = SerializationTypeEnum.getName(codec);
        Serializer extension = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension(codecName);
        if (extension == null) {
            throw new SerialException("操作不到序列化方法：" + codecName);
        }
        return extension;
    }

    private byte getCodec(byte signByte) {
        return BitUtils.getBit(signByte, 0, 4);
    }

    private byte getMessageType(byte signByte) {
        return BitUtils.getBit(signByte, 6, 1);
    }

}
