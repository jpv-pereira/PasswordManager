package com.jpvp.backend.Service;

import com.jpvp.backend.Model.Client;
import com.jpvp.backend.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientByID(long id) {
        return clientRepository.findById(id);
    }
}
