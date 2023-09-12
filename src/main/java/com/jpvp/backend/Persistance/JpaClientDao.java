package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class JpaClientDao implements ClientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Client> getAllClients() {
        return entityManager.createQuery("SELECT * FROM Client", Client.class).getResultList();
    }

    @Override
    public Client createClient(Client client) {
        Client managedClient = entityManager.merge(client);
        entityManager.persist(managedClient);
        return managedClient;
    }
}
