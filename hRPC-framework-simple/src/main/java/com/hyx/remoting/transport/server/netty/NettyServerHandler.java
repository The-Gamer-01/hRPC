package com.hyx.remoting.transport.server.netty;

import com.hyx.remoting.constants.RpcConstants;
import com.hyx.remoting.dto.RpcMessage;
import com.hyx.remoting.dto.RpcRequest;
import com.hyx.remoting.dto.RpcResponse;
import com.hyx.remoting.handler.RpcRequestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * Netty服务端处理器.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/22 13:14
 **/

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final RpcRequestHandler rpcRequestHandler;

    public NettyServerHandler() {
        this.rpcRequestHandler = new RpcRequestHandler();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof RpcMessage) {
                log.info("server receive msg: [{}]", msg);
                RpcMessage clientMsg = (RpcMessage) msg;
                RpcRequest rpcRequest = (RpcRequest) clientMsg.getData();
                Object result = rpcRequestHandler.handler(rpcRequest);

                RpcMessage rpcMessage = new RpcMessage();

                rpcMessage.setCompress(RpcConstants.GZIP);
                rpcMessage.setMessageType(RpcConstants.RESPONSE_EVENT);
                rpcMessage.setCodec(clientMsg.getCodec());
                rpcMessage.setRequestId(clientMsg.getRequestId());
                RpcResponse<Object> rpcResponse = null;
                if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                    rpcResponse = RpcResponse.success(result, rpcRequest.getRequestId());
                } else {
                    rpcResponse = RpcResponse.fail(result, null);
                    log.error("不可写，消息被取消");
                }
                rpcMessage.setData(rpcResponse);
                ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("server catch exception");
        cause.printStackTrace();
        ctx.close();
    }
}
