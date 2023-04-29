package com.example.brokerterminal.tcp.exceptions;

public class RequestTimedOutException extends RuntimeException {

    public RequestTimedOutException() {
        super();
    }

    public RequestTimedOutException(String message) {
        super(message);
    }

    public RequestTimedOutException(String message, Throwable cause) {
        super(message, cause);
    }

}
