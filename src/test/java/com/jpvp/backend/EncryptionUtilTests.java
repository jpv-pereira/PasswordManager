package com.jpvp.backend;

import com.jpvp.backend.Util.EncryptionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EncryptionUtilTests {

    @Autowired
    private EncryptionUtil encryptionUtil;

    private String password;

    @BeforeEach
    public void setup() {
        password = "password";
    }

    @Test
    public void testEncryption() {
        String testEncryptedPassword = encryptionUtil.encrypt(password);
        assertNotNull(testEncryptedPassword);
    }

    @Test
    public void testDecryption() {
        String encryptedPassword = encryptionUtil.encrypt(password);
        String testDecryptedPassword = encryptionUtil.decrypt(encryptedPassword);

        assertNotNull(testDecryptedPassword);
        assertEquals(password, testDecryptedPassword);
    }
}
