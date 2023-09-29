package com.jpvp.backend.Service;

import com.jpvp.backend.Exception.PasswordDecryptException;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Persistance.JpaUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jasypt.encryption.*;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class StoredPasswordService {
    @Autowired
    private JpaUserDao jpaUserDao;

    public List<StoredPassword> decryptStoredPasswords (List<StoredPassword> storedPasswordList) {
        for (StoredPassword storedPassword: storedPasswordList) {
            //storedPassword.setPassword(decryptPassword(storedPassword.getPassword()));
        }

        return storedPasswordList;
    }

    public String decryptPassword (String password) throws NoSuchPaddingException, NoSuchAlgorithmException {
        try {


            return password;
        } catch (Exception exception) {
            throw new PasswordDecryptException();
        }

    }

}
