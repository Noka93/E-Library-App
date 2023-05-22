package com.remidiousE.Exceptions;

public class AuthorRegistrationException extends Exception{
    public AuthorRegistrationException() {
        super();
    }

    public AuthorRegistrationException(String message) {
        super(message);
    }

    public AuthorRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorRegistrationException(Throwable cause) {
        super(cause);
    }

    protected AuthorRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
