package ru.itmo.blps.services.Exceptions;

public class WrongStatusException extends ServiceException{
    public WrongStatusException() {
        super();
    }

    public WrongStatusException(String message) {
        super(message);
    }

    public WrongStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongStatusException(Throwable cause) {
        super(cause);
    }

    protected WrongStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
