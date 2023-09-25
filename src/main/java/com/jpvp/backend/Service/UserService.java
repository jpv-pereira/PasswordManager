package com.jpvp.backend.Service;

import com.jpvp.backend.Exception.EmailTakenException;
import com.jpvp.backend.Exception.UsernameTakenException;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Persistance.JpaUserDao;
import com.jpvp.backend.Persistance.JpaUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private JpaUserDao jpaCustomerDao;

    public User createUser(User customer) {
        /*
        If the email is in use the user already has an account, more important than knowing if the username is taken
         */
        if (jpaCustomerDao.verifyExists("email", customer.getEmail(), User.class)) {
            throw new EmailTakenException();
        }

        if (jpaCustomerDao.verifyExists("userName", customer.getUserName(), User.class)) {
            throw new UsernameTakenException();
        }

        return jpaCustomerDao.createUser(customer);
    }

    public List<User> listUsers() {
        return jpaCustomerDao.getAllUsers();
    }

    public User findByUserName(String userName) {
        return jpaCustomerDao.findByUserMame(userName);
    }

    public User getClientByID(long id) {
        return null;
    }

    public void createStoredPassword(User user, StoredPassword storedPassword) {
        jpaCustomerDao.createStoredPassword(user, storedPassword);
    }

}
