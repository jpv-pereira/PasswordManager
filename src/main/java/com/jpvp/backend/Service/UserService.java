package com.jpvp.backend.Service;

import com.jpvp.backend.Exception.EmailTakenException;
import com.jpvp.backend.Exception.PasswordDecryptException;
import com.jpvp.backend.Exception.UserNotFoundException;
import com.jpvp.backend.Exception.UsernameTakenException;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Persistance.JpaUserDao;
import com.jpvp.backend.Util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private JpaUserDao jpaUserDao;

    @Autowired
    private StoredPasswordService storedPasswordService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EncryptionUtil encryptionUtil;

    public User createUser(User user) {
        /*
        If the email is in use the user already has an account, more important than knowing if the username is taken
         */
        if (jpaUserDao.verifyExists("email", user.getEmail(), User.class)) {
            throw new EmailTakenException();
        }

        if (jpaUserDao.verifyExists("username", user.getUsername(), User.class)) {
            throw new UsernameTakenException();
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new PasswordDecryptException();
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        user.setPassword(hashedPassword);

        return jpaUserDao.createUser(user);
    }

    public List<User> listUsers() {
        return jpaUserDao.getAllUsers();
    }

    public User findByUsername(String userName) {
        return jpaUserDao.findByUsername(userName);
    }

    public User findByEmail(String email) {
        return jpaUserDao.findByEmail(email);
    }

    public void createStoredPassword(String username, StoredPassword storedPassword) {
        User user = jpaUserDao.findByEmail(username);
        if (user == null) {
            throw new UserNotFoundException();
        }

        storedPassword.setPassword(encryptionUtil.encrypt(storedPassword.getPassword()));

        jpaUserDao.createStoredPassword(user, storedPassword);
    }

    public List<StoredPassword> getStoredPasswords (String email) {
        List<StoredPassword> storedPasswordList = jpaUserDao.getStoredPasswords(email);
        for (StoredPassword storedPassword: storedPasswordList) {
            storedPassword.setPassword(encryptionUtil.decrypt(storedPassword.getPassword()));
        }

        return storedPasswordList;
    }

}
