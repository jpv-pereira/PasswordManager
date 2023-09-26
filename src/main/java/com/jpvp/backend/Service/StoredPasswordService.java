package com.jpvp.backend.Service;

import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Persistance.JpaUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoredPasswordService {
    @Autowired
    private JpaUserDao jpaUserDao;

    public StoredPassword createStoredPassword (StoredPassword password) {

        return password;
    }

}
