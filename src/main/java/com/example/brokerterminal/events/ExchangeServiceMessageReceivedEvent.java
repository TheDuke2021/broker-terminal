package com.example.brokerterminal.events;

import com.example.brokerterminal.tcp.server.ExchangeServiceConnection;
import org.springframework.context.ApplicationEvent;

public class ExchangeServiceMessageReceivedEvent extends ApplicationEvent {

    private ExchangeServiceConnection connection;

    public ExchangeServiceMessageReceivedEvent(Object source, ExchangeServiceConnection connection) {
        super(source);
        this.connection = connection;
    }

    public ExchangeServiceConnection getConnection() {
        return connection;
    }
}
