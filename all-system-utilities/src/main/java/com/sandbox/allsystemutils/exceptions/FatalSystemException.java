package com.sandbox.allsystemutils.exceptions;

public class FatalSystemException extends RuntimeException {
    public FatalSystemException() {
    }

    public FatalSystemException(String message) {
        super(message);
    }

    public FatalSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public FatalSystemException(Throwable cause) {
        super(cause);
    }

    public FatalSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
