package com.hyx.remoting.transport.client;

import com.hyx.factory.SingletonFactory;
import com.hyx.remoting.dto.RpcMessage;
import com.hyx.remoting.dto.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty客户端处理类.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/21 16:59
 **/

@Slf4j
public class NettyRpcClientHandler extends ChannelInboundHandlerAdapter {

    private UnprocessedRequests unprocessedRequests;
    
    private final NettyRpcClient nettyRpcClient;

    public NettyRpcClientHandler() {
        this.nettyRpcClient = SingletonFactory.getInstance(NettyRpcClient.class);
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            log.info("msg:{}", msg);
            if (msg instanceof RpcMessage) {
                RpcMessage temp = (RpcMessage) msg;
                RpcResponse<Object> rpcResponse = (RpcResponse<Object>) temp.getData();
                unprocessedRequests.complete(rpcResponse);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端抓取到异常", cause);
        cause.printStackTrace();
        ctx.close();
    }
}
