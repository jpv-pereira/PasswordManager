package com.jpvp.backend.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class EncryptionUtil {
    @Value("${encryption.secret}")
    private String secret;

    public String encrypt(String data) {
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return null;
    }

    public String decrypt(String data) {
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] encrypted = Base64.getDecoder().decode(data);
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return null;
    }

}
