package com.jpvp.backend.Exception;

public class EmailTakenException extends RuntimeException {

    public EmailTakenException() {
        super("Email is taken");
    }
}
