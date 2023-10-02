package com.jpvp.backend.Exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super("Email was not found");
    }
}
