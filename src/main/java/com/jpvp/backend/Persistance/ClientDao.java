package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.Client;

import java.util.List;

public interface ClientDao {
    List<Client> getAllClients();

    Client createClient(Client client);

}
