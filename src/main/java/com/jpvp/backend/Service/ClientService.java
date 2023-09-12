package com.jpvp.backend.Service;

import com.jpvp.backend.Model.Client;
import com.jpvp.backend.Persistance.ClientRepository;
import com.jpvp.backend.Persistance.JpaClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private JpaClientDao jpaClientDao;

    public Client createClient(Client client) {
        return jpaClientDao.createClient(client);
    }

    public Client getClientByID(long id) {
        return null;
    }

}
