package com.example.brokerterminal.tcp.client;

import com.example.brokerterminal.proto.ExchangeInfoMessage;
import com.example.brokerterminal.proto.OwnCommand;
import com.example.brokerterminal.proto.Status;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.List;

public class ExchangeService {

    private final String identifier;
    private final String receiverIdentifier;
    private final String host;
    private final int port;
    private final List<OwnCommand> supportedCommands;
    private final Action action;
    private Status status;

    public ExchangeService(String identifier, String receiverIdentifier, String host, int port, List<OwnCommand> supportedCommands, Action action) {
        this.identifier = identifier;
        this.receiverIdentifier = receiverIdentifier;
        this.host = host;
        this.port = port;
        this.supportedCommands = supportedCommands;
        this.action = action;
    }

    public ExchangeService(String identifier, String receiverIdentifier, String host, int port, List<OwnCommand> supportedCommands, Status status, Action action) {
        this.identifier = identifier;
        this.receiverIdentifier = receiverIdentifier;
        this.host = host;
        this.port = port;
        this.supportedCommands = supportedCommands;
        this.status = status;
        this.action = action;
    }

    public void start() {
        new Thread(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline p = ch.pipeline();

                                p.addLast(new ProtobufVarint32FrameDecoder());
                                p.addLast(new ProtobufDecoder(ExchangeInfoMessage.getDefaultInstance()));

                                p.addLast(new ProtobufVarint32LengthFieldPrepender());
                                p.addLast(new ProtobufEncoder());

                                p.addLast(new ExchangeServiceHandler(identifier, receiverIdentifier, supportedCommands, status));
                            }
                        });

                Channel channel = bootstrap.connect(host, port).sync().channel();
                ExchangeServiceHandler handler = channel.pipeline().get(ExchangeServiceHandler.class);
                action.action(channel, handler);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    public interface Action {
        void action(Channel channel, ExchangeServiceHandler handler) throws Exception;
    }
}
