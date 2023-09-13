package com.jpvp.backend.Exception;

public class UsernameTakenException extends RuntimeException{

    public UsernameTakenException() {
        super("Username is taken");
    }
}
