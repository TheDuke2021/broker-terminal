package com.example.brokerterminal.tcp.server;

import com.example.brokerterminal.events.ExchangeServiceConnectionEvent;
import com.example.brokerterminal.events.ExchangeServiceMessageReceivedEvent;
import com.example.brokerterminal.proto.ExchangeInfoMessage;
import com.example.brokerterminal.proto.Header;
import com.example.brokerterminal.proto.MessageEnumsProto;
import com.example.brokerterminal.proto.Response;
import com.example.brokerterminal.tcp.exceptions.CommandTypeMismatchException;
import com.example.brokerterminal.tcp.exceptions.HandshakeTimeoutException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.context.ApplicationEventPublisher;

import java.util.*;

import static com.example.brokerterminal.proto.MessageEnumsProto.CommandType.ctHandshake;
import static com.example.brokerterminal.proto.MessageEnumsProto.CommandType.ctStatus;

@Sharable
public class TCPServerHandler extends SimpleChannelInboundHandler<ExchangeInfoMessage> {

    //EventPublisher необходим для оповевщения Spring-приложения о полученных сообщениях от сервисов биржевой информации
    private final ApplicationEventPublisher applicationEventPublisher;

    //Time-out приветствия. По условиям задачи равен 5 секундам
    private final long HANDSHAKE_TIMEOUT = 5000L;
    //Хеш-таблица, содержащая все соединения с сервисами биржевой информации
    private final Map<Channel, ExchangeServiceConnection> connections = Collections.synchronizedMap(new HashMap<>());
    //Сет содержит реквесты, которые были отправлены сервисам биржевой информации и на которые ещё не был получен Response
    private final Set<ExchangeInfoMessage> requests = Collections.synchronizedSet(new HashSet<>());
    private final String serverIdentifier;
    private volatile int messageNum = 0;

    public TCPServerHandler(ApplicationEventPublisher applicationEventPublisher, String serverIdentifier) {
        super();
        this.applicationEventPublisher = applicationEventPublisher;
        this.serverIdentifier = serverIdentifier;
    }

    //Обработка нового соединения
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        //Проверка тайм-аута на приветствие
        new Thread(() -> {
            try {
                //Через время, равное HANDSHAKE_TIMEOUT, выполняем проверку - был ли установлен handshake
                Thread.sleep(HANDSHAKE_TIMEOUT);
                if (!connections.containsKey(ctx.channel())) {
                    HandshakeTimeoutException e = new HandshakeTimeoutException(
                            String.format("Handshake time out exceeded. Remote address: %s",
                                    ctx.channel().remoteAddress()));
                    ctx.channel().close();
                    throw e;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    //Обработка входящего сообщения
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExchangeInfoMessage message) throws Exception {

        //Проверка на то, открыто ли соединение
        if (!ctx.channel().isOpen())
            return;

        //Если входящее сообщение - запрос на handshake, то обрабатываем его
        if (message.hasRequest()) {
            //Проверка типа комманды
            MessageEnumsProto.CommandType messageType = message.getRequest().getCommand();
            if (messageType != ctHandshake) {
                CommandTypeMismatchException e = new CommandTypeMismatchException(
                        String.format("CommandType of handshake-request does not equal to \"ctHandshake\". " +
                                        "Received type: %s, Expected type: %s. Remote address: %s, identifier: %s",
                                messageType,
                                ctHandshake,
                                ctx.channel().remoteAddress(),
                                message.getHeader().getSender()));
                ctx.channel().close();
                throw e;
            }

            //Если всё хорошо, и ошибок не возникло - handshake успешно произведён. Добавляем сервис в список соединений сервера
            ExchangeServiceConnection connection = new ExchangeServiceConnection(message.getHeader().getSender());
            connection.setSupportedCommands(message.getRequest().getSupportedCommandsList());
            connections.put(ctx.channel(), connection);

            //Оповещаем Spring-приложение о новом соединении с сервисом биржевой информации
            applicationEventPublisher.publishEvent(new ExchangeServiceConnectionEvent(this, connection, ExchangeServiceConnectionEvent.Type.CONNECTED));

            //Формируем и отправляем ответ сервису биржевой информации
            ExchangeInfoMessage response = ExchangeInfoMessage.newBuilder()
                    .setHeader(Header.newBuilder()
                            .setMessageNum(String.valueOf(messageNum++))
                            .setTimestamp(System.currentTimeMillis())
                            .setSender(serverIdentifier)
                            .setReceiver(connection.getIdentifier())
                            .setMessageNumAnswer(message.getHeader().getMessageNum()))
                    .setResponse(Response.newBuilder()
                            .setCommand(ctHandshake)
                            .setAnswerType(MessageEnumsProto.AnswerType.atAnswerOK))
                    .build();

            ctx.channel().writeAndFlush(response);
        }

        //Если получено сообщение от незарегистрированного соединения - игнорируем его
        ExchangeServiceConnection connection = connections.get(ctx.channel());
        if (connection == null)
            return;

        //Обработка Event
        if (message.hasEvent()) {
            //Проверка на соответствие идентификатора сервера и поля receiver сообщения
            if (!serverIdentifier.equals(message.getHeader().getReceiver()))
                return;
            connections.get(ctx.channel()).setLastStatus(message.getEvent().getStatus());
            applicationEventPublisher.publishEvent(new ExchangeServiceMessageReceivedEvent(this, message));
        }

        //Обработка Response
        if (message.hasResponse()) {

            //Ищем Request на который был получен данный Response
            String messageNumAnswer = message.getHeader().getMessageNumAnswer();
            Optional<ExchangeInfoMessage> requestOpt = requests.stream()
                    .filter(r -> r.getHeader().getMessageNum()
                            .equals(messageNumAnswer))
                    .findAny();

            //Если Request не найден (т.е. messageNumAnswer не совпал ни с одним из messageNum) - игнорируем Response
            if (requestOpt.isEmpty())
                return;

            ExchangeInfoMessage request = requestOpt.get();

            //Проверка на соответствие идентификатора сервера и поля receiver сообщения
            if (!serverIdentifier.equals(message.getHeader().getReceiver()))
                return;

            //Проверка на соответствие полей commandType у Response и Request
            if (request.getRequest().getCommand() != message.getResponse().getCommand())
                return;

            //Если все проверки пройдены - это тот Response, которого мы ожидали.
            requests.remove(request);

            //Проверяем тип ответа (answerType). Если он равен atAnswerOK - перенаправляем сообщение Spring-приложению
            if (message.getResponse().getAnswerType() != MessageEnumsProto.AnswerType.atAnswerOK)
                return;

            applicationEventPublisher.publishEvent(new ExchangeServiceMessageReceivedEvent(this, message));
        }

//        System.out.println(message);
    }

    //Обработка разъединённого соединенеия
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ExchangeServiceConnection removedConnection = connections.remove(ctx.channel());
        if (removedConnection != null)
            applicationEventPublisher.publishEvent(
                    new ExchangeServiceConnectionEvent(this, removedConnection, ExchangeServiceConnectionEvent.Type.DISCONNECTED));
    }

    public Map<Channel, ExchangeServiceConnection> getConnections() {
        return connections;
    }

    //Отправка сообщений сервисам биржевой информации
    public void sendMessage(ExchangeInfoMessage message) {
        message = message.toBuilder()
                .mergeHeader(Header.newBuilder()
                        .setSender(serverIdentifier)
                        .setMessageNum(String.valueOf(messageNum++))
                        .build())
                .build();

        String receiver = message.getHeader().getReceiver();
        Map.Entry<Channel, ExchangeServiceConnection> entry = connections.entrySet()
                .stream()
                .filter(e -> e.getValue().getIdentifier().equals(receiver))
                .findFirst()
                .orElse(null);

        if (entry == null)
            return;

        entry.getKey().writeAndFlush(message);
        if (message.getRequest().getCommand() != ctStatus)
            requests.add(message);
    }


    //Обработка исключений
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ExchangeServiceConnection removedConnection = connections.remove(ctx.channel());
        if (removedConnection != null)
            applicationEventPublisher.publishEvent(
                    new ExchangeServiceConnectionEvent(this, removedConnection, ExchangeServiceConnectionEvent.Type.DISCONNECTED));
        ctx.close();
    }
}
