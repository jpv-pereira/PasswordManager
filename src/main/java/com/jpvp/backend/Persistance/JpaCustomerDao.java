package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class JpaCustomerDao implements CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
        public List<Customer> getAllCustomers() {
        CriteriaQuery<Customer> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Customer.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer managedClient = entityManager.merge(customer);
        entityManager.persist(managedClient);
        return managedClient;
    }
}
