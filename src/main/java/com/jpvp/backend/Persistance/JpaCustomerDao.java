package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
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

    @Override
    public boolean userNameExists(String userName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
        criteriaQuery.select(criteriaBuilder.count(customerRoot));

        Predicate userNamePredicate = criteriaBuilder.equal(customerRoot.get("userName"), userName);

        criteriaQuery.where(userNamePredicate);

        TypedQuery<Long> customerTypedQuery = entityManager.createQuery(criteriaQuery);

        return customerTypedQuery.getSingleResult() > 0;
    }
}
