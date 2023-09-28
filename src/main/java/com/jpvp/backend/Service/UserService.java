package com.jpvp.backend.Service;

import com.jpvp.backend.Exception.EmailTakenException;
import com.jpvp.backend.Exception.UsernameTakenException;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Persistance.JpaUserDao;
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
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user) {
        /*
        If the email is in use the user already has an account, more important than knowing if the username is taken
         */
        if (jpaUserDao.verifyExists("email", user.getEmail(), User.class)) {
            throw new EmailTakenException();
        }

        if (jpaUserDao.verifyExists("userName", user.getUsername(), User.class)) {
            throw new UsernameTakenException();
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        user.setPassword(hashedPassword);

        return jpaUserDao.createUser(user);
    }

    public List<User> listUsers() {
        return jpaUserDao.getAllUsers();
    }

    public User findByUserName(String userName) {
        return jpaUserDao.findByUsername(userName);
    }

    public User findByEmail(String email) {
        return jpaUserDao.findByEmail(email);
    }

    public User getClientByID(long id) {
        return null;
    }

    public void createStoredPassword(String username, StoredPassword storedPassword) {
        User user = jpaUserDao.findByUsername(username);
        jpaUserDao.createStoredPassword(user, storedPassword);
    }

}
