package com.jpvp.backend.Service;

import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Persistance.ClientRepository;
import com.jpvp.backend.Persistance.JpaClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoredPasswordService {
    @Autowired
    private JpaClientDao jpaClientDao;

    public StoredPassword createStoredPassword (StoredPassword password) {

        return password;
    }

}
