package com.jpvp.backend.Exception;

public class PasswordDecryptException extends RuntimeException {

    public PasswordDecryptException() {
        super("Password failed to be decrypted");
    }
}
