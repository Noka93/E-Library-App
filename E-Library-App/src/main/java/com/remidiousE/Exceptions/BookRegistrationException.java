package com.remidiousE.Exceptions;

public class BookRegistrationException extends Exception{

    public BookRegistrationException() {
        super();
    }

    public BookRegistrationException(String message) {
        super(message);
    }

    public BookRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookRegistrationException(Throwable cause) {
        super(cause);
    }

    protected BookRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
