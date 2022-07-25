package com.hyx.remoting.transport.server.netty;

import com.hyx.config.RpcServiceConfig;
import com.hyx.factory.SingletonFactory;
import com.hyx.provider.ServiceProvider;
import com.hyx.provider.impl.ZkServiceProviderImpl;
import com.hyx.remoting.AbstractServer;
import com.hyx.remoting.transport.codec.RpcMessageDecoder;
import com.hyx.remoting.transport.codec.RpcMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Netty服务端.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/4/19 11:12
 **/

@Slf4j
@Component
public class NettyServer extends AbstractServer {
    /**
     * 服务端端口号.
     */
    public static final Integer PORT = 9898;

    /**
     * boss 线程组，用于服务端接收客户端连接.
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    
    /**
     * worker 线程组，用于服务端读写客户端数据.
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     * Server Channel.
     */
    private Channel channel;

    private final ServiceProvider serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    public void start() throws InterruptedException {
        doOpen();
    }

    @Override
    protected void doOpen() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(PORT))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                        //RpcMessage编码器
                        pipeline.addLast(new RpcMessageEncoder());
                        //RpcMessage解码器
                        pipeline.addLast(new RpcMessageDecoder());
                        //RpcMessage处理器
                        pipeline.addLast(new NettyServerHandler());
                    }
                });
        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            channel = future.channel();
            log.info("[start][Netty Server 启动在 {} 端口]", PORT);
        }
    }

    @Override
    @PreDestroy
    protected void doClose() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
