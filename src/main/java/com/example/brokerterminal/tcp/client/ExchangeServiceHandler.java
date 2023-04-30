package com.example.brokerterminal.tcp.client;

import com.example.brokerterminal.proto.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class ExchangeServiceHandler extends SimpleChannelInboundHandler<ExchangeInfoMessage> {

    private final String identifier;
    private final String receiverIdentifier;
    private final List<OwnCommand> supportedCommands;
    private Status status;
    private volatile Channel channel;
    private volatile int messageNum = 0;

    public ExchangeServiceHandler(String identifier, String receiverIdentifier, List<OwnCommand> supportedCommands) {
        super();
        this.identifier = identifier;
        this.supportedCommands = supportedCommands;
        this.receiverIdentifier = receiverIdentifier;
    }

    public ExchangeServiceHandler(String identifier, String receiverIdentifier, List<OwnCommand> supportedCommands, Status status) {
        super();
        this.identifier = identifier;
        this.supportedCommands = supportedCommands;
        this.status = status;
        this.receiverIdentifier = receiverIdentifier;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ChannelFuture sendHandshake() {
        ExchangeInfoMessage message = ExchangeInfoMessage.newBuilder()
                .setHeader(Header.newBuilder()
                        .setMessageNum(String.valueOf(messageNum++))
                        .setTimestamp(System.currentTimeMillis())
                        .setSender(identifier)
                        .setReceiver(receiverIdentifier))
                .setRequest(Request.newBuilder()
                        .setCommand(MessageEnumsProto.CommandType.ctHandshake)
                        .addAllSupportedCommands(supportedCommands))
                .build();

        return channel.writeAndFlush(message);
    }

    public ChannelFuture sendEvent(Status status) {
        ExchangeInfoMessage message = ExchangeInfoMessage.newBuilder()
                .setHeader(Header.newBuilder()
                        .setMessageNum(String.valueOf(messageNum++))
                        .setTimestamp(System.currentTimeMillis())
                        .setSender(identifier)
                        .setReceiver(receiverIdentifier))
                .setEvent(Event.newBuilder()
                        .setStatus(status))
                .build();

        return channel.writeAndFlush(message);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExchangeInfoMessage message) throws Exception {
        System.out.println(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
