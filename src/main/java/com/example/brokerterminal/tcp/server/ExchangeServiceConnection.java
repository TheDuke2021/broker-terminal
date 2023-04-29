package com.example.brokerterminal.tcp.server;

import com.example.brokerterminal.proto.AdvInfo;
import com.example.brokerterminal.proto.OwnCommand;
import com.example.brokerterminal.serializers.ExchangeServiceConnectionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

//Данный класс представляет соединение между TCP-сервером и сервисом биржевой информации
@JsonSerialize(using = ExchangeServiceConnectionSerializer.class)
public class ExchangeServiceConnection {

    //Уникальный идентификатор сервиса биржевой информации, который указывается в Header
    private final String identifier;
    //Переменная, хранящая список поддерживаемых команд для данного сервиса биржевой информации
    private List<OwnCommand> supportedCommands = new ArrayList<>();
    //Переменная, хранящая данные, полученные от сервиса биржевой информации
    private AdvInfo data = AdvInfo.getDefaultInstance();
    //Время формирования последнего сообщения от сервиса
    private long timestamp;

    public ExchangeServiceConnection(String identifier) {
        this.identifier = identifier;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public AdvInfo getData() {
        return data;
    }

    public void setData(AdvInfo data) {
        this.data = data;
    }

    public List<OwnCommand> getSupportedCommands() {
        return supportedCommands;
    }

    public void setSupportedCommands(List<OwnCommand> commands) {
        supportedCommands = commands;
    }

    public String getIdentifier() {
        return identifier;
    }

}
