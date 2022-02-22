package ru.itmo.blps.services.Exceptions;

public class NoSuchProjectException extends ServiceException{
    public NoSuchProjectException() {
        super();
    }

    public NoSuchProjectException(String message) {
        super(message);
    }

    public NoSuchProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchProjectException(Throwable cause) {
        super(cause);
    }

    protected NoSuchProjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
