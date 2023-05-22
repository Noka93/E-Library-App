package com.remidiousE.Exceptions;

public class MemberRegistrationException extends Exception{

    public MemberRegistrationException() {
        super();
    }

    public MemberRegistrationException(String message) {
        super(message);
    }

    public MemberRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberRegistrationException(Throwable cause) {
        super(cause);
    }

    protected MemberRegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
