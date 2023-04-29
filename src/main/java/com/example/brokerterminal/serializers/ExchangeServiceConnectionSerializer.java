package com.example.brokerterminal.serializers;

import com.example.brokerterminal.proto.OwnCommand;
import com.example.brokerterminal.tcp.server.ExchangeServiceConnection;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;

public class ExchangeServiceConnectionSerializer extends StdSerializer<ExchangeServiceConnection> {
    public ExchangeServiceConnectionSerializer() {
        this(null);
    }

    public ExchangeServiceConnectionSerializer(Class<ExchangeServiceConnection> t) {
        super(t);
    }

    @Override
    public void serialize(ExchangeServiceConnection connection, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("identifier", connection.getIdentifier());
        jsonGenerator.writeFieldName("supportedCommands");
        jsonGenerator.writeStartArray();
        for (OwnCommand command : connection.getSupportedCommands()) {
            jsonGenerator.writeRawValue(JsonFormat.printer().print(command));
        }
        jsonGenerator.writeEndArray();
        if (connection.getData() != null) {
            jsonGenerator.writeFieldName("lastStatus");
            jsonGenerator.writeRawValue(JsonFormat.printer().print(connection.getData()));
        }
        jsonGenerator.writeEndObject();
    }
}
