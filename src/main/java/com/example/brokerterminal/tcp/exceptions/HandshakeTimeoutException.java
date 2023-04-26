package com.example.brokerterminal.tcp.exceptions;

public class HandshakeTimeoutException extends Exception {

    public HandshakeTimeoutException() {
        super();
    }

    public HandshakeTimeoutException(String message) {
        super(message);
    }

    public HandshakeTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
