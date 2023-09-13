package com.jpvp.backend.Service;

import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Persistance.JpaCustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoredPasswordService {
    @Autowired
    private JpaCustomerDao jpaCustomerDao;

    public StoredPassword createStoredPassword (StoredPassword password) {

        return password;
    }

}
