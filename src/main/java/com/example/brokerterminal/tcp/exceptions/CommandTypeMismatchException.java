package com.example.brokerterminal.tcp.exceptions;

public class CommandTypeMismatchException extends Exception {

    public CommandTypeMismatchException() {
        super();
    }

    public CommandTypeMismatchException(String message) {
        super(message);
    }

    public CommandTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
