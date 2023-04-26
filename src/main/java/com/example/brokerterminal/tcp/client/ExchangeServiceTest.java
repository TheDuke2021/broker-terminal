package com.example.brokerterminal.tcp.client;

import com.example.brokerterminal.proto.*;

import java.util.ArrayList;
import java.util.List;

public class ExchangeServiceTest {

    public static void main(String[] args) throws InterruptedException {
        //Список поддерживаемых комманд
        List<OwnCommand> supportedCommands = new ArrayList<>();
        supportedCommands.add(OwnCommand.newBuilder()
                .setAlias("buy")
                .setCaption("Купить")
                .setDescription("Выполняет биржевую оперцаию по покупке предмета")
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
                                        .addValues(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtString)
                                                .setValue("Акция компании \"Главный банк\""))
                                        .addValues(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue("50000"))
                                        .addValues(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue("190")))
                                .addRows(DataRow.newBuilder()
                                        .setRowIdent("2")
                                        .setIncrementDelete(false)
                                        .addValues(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtString)
                                                .setValue("Акция компании \"Теле.ком\""))
                                        .addValues(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue("1000.85"))
                                        .addValues(ValueRef.newBuilder()
                                                .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                .setValue("12")))))
                .build();


        ExchangeService.Action action = (channel, handler) -> {
            handler.sendHandshake().sync();
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
                                        .setFullOrIncrement(false)
                                        .addRows(DataRow.newBuilder()
                                                .setRowIdent("1")
                                                .setIncrementDelete(false)
                                                .addValues(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtString)
                                                        .setValue("Акция компании \"Главный банк\""))
                                                .addValues(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.valueOf((int) (Math.random() * 50000 + 20000))))
                                                .addValues(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.valueOf((int) (Math.random() * 50)))))
                                        .addRows(DataRow.newBuilder()
                                                .setRowIdent("2")
                                                .setIncrementDelete(false)
                                                .addValues(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtString)
                                                        .setValue("Акция компании \"Теле.ком\""))
                                                .addValues(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.format("%.2f", Math.random() * 1000)))
                                                .addValues(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue("12")))
                                        .addRows(DataRow.newBuilder()
                                                .setRowIdent("2")
                                                .setIncrementDelete(false)
                                                .addValues(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtString)
                                                        .setValue("Ценные бумаги"))
                                                .addValues(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.format("%.2f", Math.random() * 1000)))
                                                .addValues(ValueRef.newBuilder()
                                                        .setDataType(MessageEnumsProto.DataType.dtFloat)
                                                        .setValue(String.valueOf((int) (Math.random() * 100 + 15)))))))
                        .build();
                handler.sendEvent(status1);
                Thread.sleep((long) (Math.random() * 1000 + 1000));
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


        supportedCommands = new ArrayList<>();

        supportedCommands.add(OwnCommand.newBuilder()
                .setAlias("send")
                .setCaption("Отправить данные")
                .setDescription("Отправляет личные данные пользователя")
                .addParameters(Parameter.newBuilder()
                        .setAlias("param1")
                        .setCaption("Username")
                        .setValue(ValueRef.newBuilder()
                                .setDataType(MessageEnumsProto.DataType.dtString)
                                .setValue("")))
                .addParameters(Parameter.newBuilder()
                        .setAlias("param2")
                        .setCaption("Password")
                        .setValue(ValueRef.newBuilder()
                                .setDataType(MessageEnumsProto.DataType.dtString)
                                .setValue("")))
                .build());

        supportedCommands.add(OwnCommand.newBuilder()
                .setAlias("delete")
                .setCaption("Удалить данные с сервера")
                .setDescription("Навсегда удаляет личные данные")
                .build());

        ExchangeService.Action action1 = (channel, handler) -> {
            handler.sendHandshake().sync();
            while (true) {
                Status status1 = status.toBuilder()
                        .setAdvStatus(AdvInfo.newBuilder()
                                .setCaption("Товары")
                                .addFields(AdvInfoFieldRef.newBuilder()
                                        .setAlias("name")
                                        .setCaption("Товары")
                                        .setDataType(MessageEnumsProto.DataType.dtString))
                                .addFields(AdvInfoFieldRef.newBuilder()
                                        .setAlias("price")
                                        .setCaption("Услуга")
                                        .setDataType(MessageEnumsProto.DataType.dtFloat))
                                .addFields(AdvInfoFieldRef.newBuilder()
                                        .setAlias("quantity")
                                        .setCaption("Доступно")
                                        .setDataType(MessageEnumsProto.DataType.dtFloat))
                                .addFields(AdvInfoFieldRef.newBuilder()
                                        .setAlias("quantity")
                                        .setCaption("Дата")
                                        .setDataType(MessageEnumsProto.DataType.dtFloat))
                                .setData(AdvInfoData.newBuilder()
                                        .addRows(DataRow.newBuilder()
                                                .addValues(ValueRef.newBuilder()
                                                        .setValue("Золото"))
                                                .addValues(ValueRef.newBuilder()
                                                        .setValue("Страхование"))
                                                .addValues(ValueRef.newBuilder()
                                                        .setValue("Да"))
                                                .addValues(ValueRef.newBuilder()
                                                        .setValue("27.04.2023")))))
                        .build();
                handler.sendEvent(status1);
                Thread.sleep((long) (Math.random() * 1000 + 1000));
            }
        };


        new ExchangeService(
                "Сервис номер 2",
                "server",
                "localhost",
                10000,
                supportedCommands,
                status,
                action1
        ).start();


    }
}
