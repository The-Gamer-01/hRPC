package com.hyx.remoting.transport.client;

import com.hyx.extension.ExtensionLoader;
import com.hyx.factory.SingletonFactory;
import com.hyx.registry.ServiceDiscovery;
import com.hyx.remoting.RpcRequestTransport;
import com.hyx.remoting.dto.RpcMessage;
import com.hyx.remoting.dto.RpcRequest;
import com.hyx.remoting.dto.RpcResponse;
import com.hyx.remoting.transport.codec.RpcMessageDecoder;
import com.hyx.remoting.transport.codec.RpcMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty客户端.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/19 18:59
 **/

@Slf4j
public class NettyRpcClient implements RpcRequestTransport {

    private final Bootstrap bootstrap;

    private final EventLoopGroup eventExecutors;

    private final UnprocessedRequests unprocessedRequests;

    private final ServiceDiscovery serviceDiscovery;

    private final ChannelProvider channelProvider;

    private AtomicInteger requestId = new AtomicInteger(0);

    public NettyRpcClient() {
        eventExecutors = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS))
                                .addLast(new RpcMessageEncoder())
                                .addLast(new RpcMessageDecoder())
                                .addLast(new NettyRpcClientHandler());
                    }
                });
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.channelProvider = SingletonFactory.getInstance(ChannelProvider.class);
    }
    
    /**
     * 进行连接.
     * @param inetSocketAddress 连接地址
     */
    @SneakyThrows
    public Channel doConnect(InetSocketAddress inetSocketAddress) {
        CompletableFuture<Channel> channelCompletableFuture = new CompletableFuture<>();
        bootstrap.connect(inetSocketAddress)
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        log.info("客户端连接[{}]成功", inetSocketAddress.toString());
                        channelCompletableFuture.complete(future.channel());
                    } else {
                        throw new IllegalStateException();
                    }
                });
        return channelCompletableFuture.get();
    }

    public Channel getChannel(InetSocketAddress inetSocketAddress) {
        Channel channel = channelProvider.get(inetSocketAddress);
        if (channel == null) {
            channel = doConnect(inetSocketAddress);
            channelProvider.set(inetSocketAddress, channel);
        }
        return channel;
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        CompletableFuture<RpcResponse<Object>> resultFuture = new CompletableFuture<>();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 9898);
        Channel channel = getChannel(inetSocketAddress);
        if (channel.isActive()) {
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
            RpcMessage rpcMessage = RpcMessage.builder()
                    .codec((byte) 0x01)
                    .requestId(requestId.incrementAndGet())
                    .data(rpcRequest)
                    .build();
            channel.writeAndFlush(rpcMessage)
                    .addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            log.info("客户端发送消息：[{}]", rpcMessage);
                        } else {
                            future.channel().close();
                            resultFuture.completeExceptionally(future.cause());
                            log.error("发送失败：", future.cause());
                        }
                    });
        } else {
            throw new IllegalStateException();
        }
        return resultFuture;
    }
}
