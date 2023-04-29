package com.example.brokerterminal.tcp.exceptions;

public class HandshakeTimedOutException extends Exception {

    public HandshakeTimedOutException() {
        super();
    }

    public HandshakeTimedOutException(String message) {
        super(message);
    }

    public HandshakeTimedOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
