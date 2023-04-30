package com.example.brokerterminal.websockets;

import com.example.brokerterminal.events.ExchangeServiceMessageReceivedEvent;
import com.example.brokerterminal.proto.ExchangeInfoMessage;
import com.example.brokerterminal.tcp.server.TCPServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessagesWebSocket extends TextWebSocketHandler {

    private final TCPServer tcpServer;
    private final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());
    //Jackson JSON-конвертер
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MessagesWebSocket(TCPServer tcpServer) {
        this.tcpServer = tcpServer;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException, InterruptedException {
        sessions.add(session);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ExchangeInfoMessage.Builder exchangeInfoMessage = ExchangeInfoMessage.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(message.getPayload(), exchangeInfoMessage);
        tcpServer.sendMessage(exchangeInfoMessage.build());
    }

    @EventListener
    public void handleMessageFromExchangeService(ExchangeServiceMessageReceivedEvent event) throws IOException {
        sendToAll(objectMapper.writeValueAsString(event.getConnection()));
    }

    private void sendToAll(String message) throws IOException {
        for (WebSocketSession session : sessions) {
            synchronized (session) {
                if (session.isOpen())
                    session.sendMessage(new TextMessage(message));
            }
        }
    }
}
