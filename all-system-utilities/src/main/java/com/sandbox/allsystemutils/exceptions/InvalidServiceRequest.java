package com.sandbox.allsystemutils.exceptions;

public class InvalidServiceRequest extends RuntimeException {
    public InvalidServiceRequest() {
    }

    public InvalidServiceRequest(String message) {
        super(message);
    }

    public InvalidServiceRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidServiceRequest(Throwable cause) {
        super(cause);
    }

    public InvalidServiceRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
