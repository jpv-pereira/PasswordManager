package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.User;
import com.jpvp.backend.Model.StoredPassword;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User createUser(User customer);

    User findByUserMame(String userName);

    User findByEmail(String email);

    <T> boolean verifyExists(String rowName, String verifyString, Class<T> type);

    void createStoredPassword(User customer, StoredPassword storedPassword);

    List<StoredPassword> getStoredPasswords(User user);

}
