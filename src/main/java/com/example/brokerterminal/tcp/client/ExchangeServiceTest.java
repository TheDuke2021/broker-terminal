package com.example.brokerterminal.tcp.client;

import com.example.brokerterminal.proto.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExchangeServiceTest {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final Random random = new Random();

    public static void main(String[] args) {
        //Список поддерживаемых комманд
        List<OwnCommand> supportedCommands = new ArrayList<>();
        supportedCommands.add(OwnCommand.newBuilder()
                .setAlias("buy")
                .setCaption("Купить")
                .setDescription("Выполняет биржевую операцию по покупке предмета")
                .addParameters(Parameter.newBuilder()
                        .setAlias("param1")
                        .setCaption("Количество")
                        .setHint("Этот параметр ни за что не отвечает")
                        .setValue(ValueRef.newBuilder()
                                .setDataType(MessageEnumsProto.DataType.dtInteger)
                                .setFormat("Целое число")
                                .setValue("500")))
                .build());

        supportedCommands.add(OwnCommand.newBuilder()
                .setAlias("sell")
                .setCaption("Продать")
                .setDescription("Выполняет биржевую операцию по продаже предмета")
                .addParameters(Parameter.newBuilder()
                        .setAlias("param1")
                        .setCaption("Количество")
                        .setValue(ValueRef.newBuilder()
                                .setDataType(MessageEnumsProto.DataType.dtInteger)
                                .setFormat("Целое число")
                                .setValue("")))
                .addParameters(Parameter.newBuilder()
                        .setAlias("param2")
                        .setCaption("Максимум")
                        .setValue(ValueRef.newBuilder()
                                .setDataType(MessageEnumsProto.DataType.dtInteger)
                                .setFormat("Целое число")
                                .setValue("")))
                .addParameters(Parameter.newBuilder()
                        .setAlias("param3")
                        .setCaption("Название")
                        .setValue(ValueRef.newBuilder()
                                .setDataType(MessageEnumsProto.DataType.dtString)
                                .setValue("")))
                .build());

        //Сообщение-статус
        Status status = Status.newBuilder()
                .setType(MessageEnumsProto.StatusType.stReady)
                .setDetails("Описание")
                .setNextTime(15)
                .setAdvStatus(AdvInfo.newBuilder()
                        .setCaption("Предметы")
                        .addFields(AdvInfoFieldRef.newBuilder()
                                .setAlias("name")
                                .setCaption("Название")
                                .setDataType(MessageEnumsProto.DataType.dtString))
                        .addFields(AdvInfoFieldRef.newBuilder()
                                .setAlias("price")
                                .setCaption("Цена")
                                .setDataType(MessageEnumsProto.DataType.dtFloat))
                        .addFields(AdvInfoFieldRef.newBuilder()
                                .setAlias("quantity")
                                .setCaption("Доступно")
                                .setDataType(MessageEnumsProto.DataType.dtFloat))
                        .setData(AdvInfoData.newBuilder()
                                .setFullOrIncrement(false)
                                .addRows(DataRow.newBuilder()
                                        .setRowIdent("1")
                                        .setIncrementDelete(false)
                                        .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtString)
                                                .setValue("Акция компании \"Главный банк\"")))
                                        .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue("50000")))
                                        .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue("190"))))
                                .addRows(DataRow.newBuilder()
                                        .setRowIdent("2")
                                        .setIncrementDelete(false)
                                        .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtString)
                                                .setValue("Акция компании \"Теле.ком\"")))
                                        .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue("1000.85")))
                                        .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue("12"))))
                                .addRows(DataRow.newBuilder()
                                        .setRowIdent("3")
                                        .setIncrementDelete(true)
                                        .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtString)
                                                .setValue("Ценные бумаги")))
                                        .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue(String.format("%.2f", Math.random() * 1000))))
                                        .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue(String.valueOf((int) (Math.random() * 100 + 15))))))))
                .build();


        ExchangeService.Action action = (channel, handler) -> {
            handler.sendHandshake().sync();
            handler.sendEvent(status).sync();
            Thread.sleep(3000);
            while (true) {
                Status status1 = status.toBuilder()
                        .setAdvStatus(AdvInfo.newBuilder()
                                .setCaption("Предметы")
                                .addFields(AdvInfoFieldRef.newBuilder()
                                        .setAlias("name")
                                        .setCaption("Название")
                                        .setDataType(MessageEnumsProto.DataType.dtString))
                                .addFields(AdvInfoFieldRef.newBuilder()
                                        .setAlias("price")
                                        .setCaption("Цена")
                                        .setDataType(MessageEnumsProto.DataType.dtFloat))
                                .addFields(AdvInfoFieldRef.newBuilder()
                                        .setAlias("quantity")
                                        .setCaption("Доступно")
                                        .setDataType(MessageEnumsProto.DataType.dtFloat))
                                .setData(AdvInfoData.newBuilder()
                                        .setFullOrIncrement(true)
                                        .addRows(DataRow.newBuilder()
                                                .setRowIdent("1")
                                                .setIncrementDelete(false)
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtString)
                                                        .setValue("Акция компании \"Главный банк\"")))
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.valueOf((int) (Math.random() * 50000 + 20000)))))
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.valueOf((int) (Math.random() * 50))))))
                                        .addRows(DataRow.newBuilder()
                                                .setRowIdent("2")
                                                .setIncrementDelete(false)
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtString)
                                                        .setValue("Акция компании \"Теле.ком\"")))
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.format("%.2f", Math.random() * 1000))))
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue("12"))))
                                        .addRows(DataRow.newBuilder()
                                                .setRowIdent("3")
                                                .setIncrementDelete(true)
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtString)
                                                        .setValue("Ценные бумаги")))
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.format("%.2f", Math.random() * 1000))))
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.valueOf((int) (Math.random() * 100 + 15))))))
                                        .addRows(DataRow.newBuilder()
                                                .setRowIdent("4")
                                                .setIncrementDelete(false)
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtString)
                                                        .setValue("Фондовый пакет")))
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.format("%.2f", Math.random() * 1000))))
                                                .addValues(DataFieldValue.newBuilder().setValue(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.valueOf((int) (Math.random() * 100 + 15))))))))
                        .build();
                handler.sendEvent(status1);
                Thread.sleep(1000);
            }
        };

        ExchangeService service1 = new ExchangeService(
                "service1",
                "server",
                "localhost",
                10000,
                supportedCommands,
                status,
                action);

        service1.start();

    }
}
