package com.remidiousE.Exceptions;

public class AdminRegistrationException extends Exception{
    public AdminRegistrationException() {
        super();
    }

    public AdminRegistrationException(String message) {
        super(message);
    }

    public AdminRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminRegistrationException(Throwable cause) {
        super(cause);
    }

    protected AdminRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
