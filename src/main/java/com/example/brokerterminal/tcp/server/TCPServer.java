package com.example.brokerterminal.tcp.server;

import com.example.brokerterminal.proto.ExchangeInfoMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

//TCP-сервер, взаимодействующий с сервисами биржевой информации
public class TCPServer {

    //Идентификатор сервера
    private final String identifier;
    //Порт сервера
    private final int port;
    //EventPublisher необходим для оповевщения Spring-приложения о полученных сообщениях от сервисов биржевой информации
    private final ApplicationEventPublisher applicationEventPublisher;
    //Обработчик событий
    private TCPServerHandler handler;


    public TCPServer(String identifier, int port, ApplicationEventPublisher applicationEventPublisher) {
        this.identifier = identifier;
        this.port = port;
        this.applicationEventPublisher = applicationEventPublisher;
        handler = new TCPServerHandler(applicationEventPublisher, identifier);
    }

    public void start() {
        new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) {
                                ChannelPipeline p = ch.pipeline();

                                p.addLast(new ProtobufVarint32FrameDecoder());
                                p.addLast(new ProtobufDecoder(ExchangeInfoMessage.getDefaultInstance()));

                                p.addLast(new ProtobufVarint32LengthFieldPrepender());
                                p.addLast(new ProtobufEncoder());
                                p.addLast(handler);
                            }
                        });

                Channel channel = bootstrap.bind(port).sync().channel();
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    public List<ExchangeServiceConnection> getExchangeServiceConnections() {
        return handler.getConnections().values().stream().toList();
    }

    public void sendMessage(ExchangeInfoMessage message) {
        handler.sendMessage(message);
    }
}
