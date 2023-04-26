package com.example.brokerterminal.tcp.exceptions;

public class MessageNumMismatchException extends Exception {

    public MessageNumMismatchException() {
        super();
    }

    public MessageNumMismatchException(String message) {
        super(message);
    }

    public MessageNumMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
