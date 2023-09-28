package com.jpvp.backend.Exception;

public class TokenValidationException extends RuntimeException {

    public TokenValidationException() {
        super("Token failed to validate");
    }
}
