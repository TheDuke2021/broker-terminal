package com.example.brokerterminal.websockets;

import com.example.brokerterminal.tcp.server.TCPServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TCPServer server;

    public WebSocketConfig(TCPServer server) {
        this.server = server;
    }

    @Bean
    public MessagesWebSocket messagesWebSocket() {
        return new MessagesWebSocket(server);
    }

    @Bean
    public NotificationsWebSocket notificationsWebSocket() {
        return new NotificationsWebSocket(server);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messagesWebSocket(), "/messages");
        registry.addHandler(notificationsWebSocket(), "/notifications");
    }
}
