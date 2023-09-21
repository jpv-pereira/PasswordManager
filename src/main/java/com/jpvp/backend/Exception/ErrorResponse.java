package com.jpvp.backend.Exception;

public class ErrorResponse {
    private String exception;
    private String message;

    public ErrorResponse(String exception, String message) {
        this.exception = exception;
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }
}
