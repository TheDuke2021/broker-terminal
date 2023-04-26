package com.example.brokerterminal.events;

import com.example.brokerterminal.tcp.server.ExchangeServiceConnection;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.ApplicationEvent;

@JsonIgnoreProperties(value = {"source", "timestamp"})
public class ExchangeServiceConnectionEvent extends ApplicationEvent {

    private final ExchangeServiceConnection connection;
    private final Type type;

    public ExchangeServiceConnectionEvent(Object source, ExchangeServiceConnection connection, Type type) {
        super(source);
        this.connection = connection;
        this.type = type;
    }

    public ExchangeServiceConnection getConnection() {
        return connection;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        CONNECTED, DISCONNECTED
    }
}
