package com.hyx.remoting.transport.codec;

import com.hyx.extension.SPI;
import com.hyx.remoting.dto.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author hyx
 **/

@SPI
public interface Codec {
    
    /**
     * 编码方法.
     * @param channelHandlerContext ChannelHandlerContext.
     * @param rpcMessage RPC message.
     * @param out ByteBuf.
     */
    void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf out);
    
    /**
     * 解码方法.
     * @param ctx ChannelHandlerContext.
     * @param in ByteBuf.
     * @return 解码后的对象.
     */
    Object decode(ChannelHandlerContext ctx, ByteBuf in);
}
