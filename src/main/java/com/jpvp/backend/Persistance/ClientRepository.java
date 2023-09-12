package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository  extends CrudRepository<Client, Long> {
    List<Client> findByLastName(String lastName);

    Client findById(long id);
}
