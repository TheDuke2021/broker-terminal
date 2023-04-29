'use strict'

//URL-адрес хоста
const URL = window.location.host;

//Перемнная, в которой в JSON-формате хранится информация о подключенных сервисах биржевой информации,
//их поддерживаемых командах и данных
let services = [];

//DOM-элементы
let serviceSelect;
let commandsTab;
let statusTab;

//Сокеты
let messagesSocket;
let notificationsSocket;


//Подключение сокетов
function connectToWebSockets() {
    messagesSocket = new WebSocket("ws://" + URL + "/messages");

    messagesSocket.onmessage = function (messageEvent) {
        const message = JSON.parse(messageEvent.data);
        console.log(message);
        const serviceIdentifier = message.identifier;
        for (let i = 0; i < services.length; i++) {
            if (services[i].identifier === serviceIdentifier) {
                services[i].data = message.data;
                const receivedTimestamp = Date.now();
                services[i].latency = receivedTimestamp - message.timestamp;

                break;
            }
        }

        if (serviceSelect.value === serviceIdentifier)
            updateStatusTab();
    };


    notificationsSocket = new WebSocket("ws://" + URL + "/notifications");


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

    if (serviceSelect.options.length < 1) {
        let option = document.createElement("option");
        option.value = "none";
        option.innerHTML = "Нет доступных серверов БИ";
        serviceSelect.appendChild(option);
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

    if (!service || Object.keys(service.data).length === 0)
        return;


    latency.innerHTML = "Задержка: " + service.latency + "мс";

    const headFields = service.data.fields;

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
    for (let dataRow of service.data.data.rows) {
        let tr = document.createElement("tr");
        for (let valueRef of dataRow.values) {
            let td = document.createElement("td");
            td.innerHTML = valueRef.value.value;
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

    if (!service || service.data == null)
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

                    if (serviceSelect.options.length < 1) {
                        let option = document.createElement("option");
                        option.value = "none";
                        option.innerHTML = "Нет доступных серверов БИ";
                        serviceSelect.appendChild(option);

                        document.querySelector("#status-tab-pane__content__update-status-button").disabled = true;
                        document.querySelector("#commands-tab-pane__content__send-button").disabled = true;
                    }

                    break;
                }
            }
            break;
        case "CONNECTED":
            if (serviceSelect.options.length == 1) {
                if (serviceSelect.value === "none")
                    serviceSelect.innerHTML = "";
            }
            let option = document.createElement("option");
            option.value = serviceToCheck.identifier;
            option.innerHTML = serviceToCheck.identifier;
            serviceSelect.appendChild(option);
            if (serviceSelect.options.length < 2)
                serviceSelect.dispatchEvent(new Event("change"));

            document.querySelector("#status-tab-pane__content__update-status-button").disabled = false;
            document.querySelector("#commands-tab-pane__content__send-button").disabled = false;

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

    connectToWebSockets();


}