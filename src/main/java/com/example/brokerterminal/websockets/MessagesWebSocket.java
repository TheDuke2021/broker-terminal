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
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessagesWebSocket extends TextWebSocketHandler {

    private final TCPServer tcpServer;
    private List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());
    //Jackson JSON-конвертер
    private ObjectMapper objectMapper = new ObjectMapper();

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
        ExchangeInfoMessage.Builder eim = ExchangeInfoMessage.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(message.getPayload(), eim);
        tcpServer.sendMessage(eim.build());
    }

    @EventListener
    public void handleMessageFromExchangeService(ExchangeServiceMessageReceivedEvent event) throws IOException {
        sendToAll(JsonFormat.printer().print(event.getMessage()));
    }

    private void sendToAll(String message) throws IOException {
        for (WebSocketSession session : sessions) {
            synchronized (session) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }
}
