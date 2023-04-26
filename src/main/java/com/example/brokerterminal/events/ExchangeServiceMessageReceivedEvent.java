package com.example.brokerterminal.events;

import com.example.brokerterminal.proto.ExchangeInfoMessage;
import org.springframework.context.ApplicationEvent;

public class ExchangeServiceMessageReceivedEvent extends ApplicationEvent {

    private ExchangeInfoMessage message;

    public ExchangeServiceMessageReceivedEvent(Object source, ExchangeInfoMessage message) {
        super(source);
        this.message = message;
    }

    public ExchangeInfoMessage getMessage() {
        return message;
    }
}
