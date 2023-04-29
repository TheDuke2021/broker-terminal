'use strict'

//URL-адрес хоста
const URL = window.location.host;

//Перемнная, в которой в JSON-формате хранится информация о подключенных сервисах биржевой информации,
//их поддерживаемых командах и последнем присланном Status-объекте
let services = [];

//DOM-элементы
let serviceSelect;
let commandsTab;
let statusTab;

//Сокеты
let messagesSocket;
let notificationsSocket;


function connectToWebSockets() {
    messagesSocket = new WebSocket("ws://" + URL + "/messages");

    messagesSocket.onmessage = function (messageEvent) {
        const message = JSON.parse(messageEvent.data);
        const serviceIdentifier = message.header.sender;
        for (let i = 0; i < services.length; i++) {
            if (services[i].identifier === serviceIdentifier) {
                const messageBody = message.event === undefined ? message.response : message.event;
                services[i].lastStatus = messageBody.status;
                const receivedTimestamp = Date.now();
                services[i].latency = receivedTimestamp - message.header.timestamp;

                break;
            }
        }

        if (serviceSelect.value === serviceIdentifier)
            updateStatusTab();
    };


    notificationsSocket = new WebSocket("ws://" + URL + "/notifications");

    notificationsSocket.onopen = function () {
        document.querySelector("#connect-to-tcp-button").disabled = true;
        //TODO toast message "Успешно подключились к TCP-серверу"
        //Пока что просто вывод в консоль (для отладки)
        console.log("Connected to TCP-server");
    }

    notificationsSocket.onmessage = function (messageEvent) {
        const message = JSON.parse(messageEvent.data);
        //Если мы получили массив данных - значим, нам пришли элементы ExchangeServiceConnection на первоначальную загрузку страницы
        if (Array.isArray(message)) {
            services = message;
            loadServicesOnPage(message);
        } else {
            //Иначе - получено сообщение о новом соединение (или разрыве старого соединения) с сервисом биржевой информации
            //TODO toast message о новом соединении или разрыве старого
            let service = message.connection;
            switch (message.type) {
                case "DISCONNECTED":
                    console.log("Exchange service disconnected: " + service.identifier);
                    for (let i = 0; i < services.length; i++) {
                        if (service.identifier === services[i]) {
                            services.slice(i, 1);
                            break;
                        }
                    }
                    break;
                case "CONNECTED":
                    console.log("New exchange service connected: " + service.identifier);
                    services.push(service);
                    break;
            }
            updateServiceSelect(service, message.type);

        }
    }
}

function loadServicesOnPage(services) {
    serviceSelect.innerHTML = "";
    for (let service of services) {
        let option = document.createElement("option");
        option.setAttribute("value", service.identifier);
        option.innerHTML = service.identifier;
        serviceSelect.add(option);
    }

    updateCommandsTab();
    updateStatusTab();
}

function updateStatusTab() {
    const identifier = serviceSelect.value;
    const service = services.find(s => s.identifier === identifier);
    const latency = document.querySelector("#status-latency");
    const table = document.querySelector("table");
    table.innerHTML = "";

    if (!service)
        return;

    const lastStatus = service.lastStatus;
    if (!lastStatus || !lastStatus.advStatus)
        return;

    latency.innerHTML = "Задержка: " + service.latency + "мс";

    const headFields = lastStatus.advStatus.fields;

    let head = document.createElement("thead");
    let headRow = document.createElement("tr");
    for (let headField of headFields) {
        let headColumn = document.createElement("th");
        headColumn.setAttribute("scope", "col");
        headColumn.innerHTML = headField.caption;

        headRow.appendChild(headColumn);
    }

    head.appendChild(headRow);
    table.appendChild(head);

    let body = document.createElement("tbody");
    //Заполнение данных
    const data = lastStatus.advStatus.data;
    //ИНКРЕМЕНТАЛЬНОЕ ОБНОВЛЕНИЕ ДАННЫХ НЕ ПОДДЕРЖИВАЕТСЯ
    for (let dataRow of data.rows) {
        let tr = document.createElement("tr");
        for (let valueRef of dataRow.values) {
            let td = document.createElement("td");
            td.innerHTML = valueRef.value;
            tr.appendChild(td);
        }
        body.appendChild(tr);
    }
    table.appendChild(body);

}

function updateCommandsTab() {
    const service = services.find(s => s.identifier === serviceSelect.value);
    let commandSelect = document.querySelector("#commands-tab-pane__content__select");

    commandSelect.innerHTML = "";
    let commandDescription = document.querySelector("#commands-tab-pane__content__description");
    commandDescription.innerHTML = "";
    let commandParameters = document.querySelector("#commands-tab-pane__content__parameters");
    commandParameters.innerHTML = "";

    if (!service)
        return;

    const supportedCommands = service.supportedCommands;

    for (const cmd of supportedCommands) {
        let option = document.createElement("option");
        option.value = cmd.alias;
        option.innerHTML = cmd.caption;
        commandSelect.appendChild(option);
    }


    commandSelect.addEventListener("change", () => {
        const cmd = supportedCommands.find(c => c.alias === commandSelect.value);

        if (!cmd)
            return;

        commandDescription.innerHTML = cmd.description;
        commandParameters.innerHTML = "";

        for (let param of cmd.parameters) {
            let row = document.createElement("div");
            row.classList.add("row");
            let paramKey = document.createElement("col");
            paramKey.classList.add("col");
            paramKey.innerHTML = param.caption;

            let paramValue = document.createElement("col");
            paramValue.classList.add("col");
            let paramValueInput = document.createElement("input");
            paramValueInput.type = "text";
            paramValueInput.size = 10;
            paramValueInput.id = param.alias;
            paramValueInput.value = param.value.value;
            paramValueInput.classList.add("form-control");
            paramValue.appendChild(paramValueInput);

            row.appendChild(paramKey);
            row.appendChild(paramValue);

            commandParameters.appendChild(row);
        }
    });

    commandSelect.dispatchEvent(new Event("change"));
}

function updateServiceSelect(serviceToCheck, type) {
    const previousId = serviceSelect.value;

    switch (type) {
        case "DISCONNECTED":
            for (let option of serviceSelect.querySelectorAll("option")) {
                if (serviceToCheck.identifier === option.value) {
                    option.remove();
                    if (previousId === serviceToCheck.identifier)
                        serviceSelect.dispatchEvent(new Event("change"));

                    break;
                }
            }
            break;
        case "CONNECTED":
            let option = document.createElement("option");
            option.value = serviceToCheck.identifier;
            option.innerHTML = serviceToCheck.identifier;
            serviceSelect.appendChild(option);
            if (serviceSelect.options.length < 2)
                serviceSelect.dispatchEvent(new Event("change"));
            break;
    }
}


function sendCommandRequest() {
    let message = {};
    message.header = {
        "timestamp": Date.now(),
        "receiver": serviceSelect.value
    }


    let params = [];
    for (let div of document.querySelectorAll("#commands-tab-pane__content__parameters .row")) {
        let param = {};
        param.alias = div.querySelector("input").id;
        param.value = {
            "value": div.querySelector("input").value
        }

        params.push(param);

    }


    message.request = {
        "command": "ctExecCommand",
        "commandForExec": {
            "alias": commandsTab.querySelector("#commands-tab-pane__content__select").value,
            "parameters": params
        }
    }

    console.log(message);
    messagesSocket.send(JSON.stringify(message));
}

function sendStatusRequest() {
    let message = {};
    message.header = {
        "timestamp": Date.now(),
        "receiver": serviceSelect.value
    }


    message.request = {
        "command": "ctStatus",
    }

    console.log(message);
    messagesSocket.send(JSON.stringify(message));
}

window.onload = () => {
    serviceSelect = document.querySelector("#service-select");
    serviceSelect.addEventListener("change", () => {
        updateStatusTab();
        updateCommandsTab();
    });
    commandsTab = document.querySelector("#commands-tab-pane__content");
    statusTab = document.querySelector("#status-tab-pane__content");

    document.querySelector("#commands-tab-pane__content__send-button").addEventListener("click", sendCommandRequest);
    document.querySelector("#status-tab-pane__content__update-status-button").addEventListener("click", sendStatusRequest);
    document.querySelector("#connect-to-tcp-button").addEventListener("click", connectToWebSockets);


}