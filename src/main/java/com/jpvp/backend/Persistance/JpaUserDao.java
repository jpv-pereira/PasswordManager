package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.User;
import com.jpvp.backend.Model.StoredPassword;
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
public class JpaUserDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
        public List<User> getAllUsers() {
        CriteriaQuery<User> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(User.class);
        Root<User> customerRoot = criteriaQuery.from(User.class);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public User createUser(User customer) {
        User managedClient = entityManager.merge(customer);
        entityManager.persist(managedClient);
        return managedClient;
    }

    @Override
    public User findByUserMame(String userName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> customerRoot = criteriaQuery.from(User.class);

        Predicate userNamePredicate = criteriaBuilder.equal(customerRoot.get("userName"), userName);

        criteriaQuery.where(userNamePredicate);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public <T> boolean verifyExists(String rowName, String verifyString, Class<T> type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<T> customerRoot = criteriaQuery.from(type);
        criteriaQuery.select(criteriaBuilder.count(customerRoot));

        Predicate existsPredicate = criteriaBuilder.equal(customerRoot.get(rowName), verifyString);

        criteriaQuery.where(existsPredicate);

        TypedQuery<Long> customerTypedQuery = entityManager.createQuery(criteriaQuery);

        return customerTypedQuery.getSingleResult() > 0;
    }

    @Override
    public void createStoredPassword(User customer, StoredPassword storedPassword) {
        StoredPassword managedPassword = entityManager.merge(storedPassword);

        List<StoredPassword> storedPasswordList = customer.getStoredPasswordList();
        storedPasswordList.add(managedPassword);

        customer.setStoredPasswordList(storedPasswordList);

        entityManager.persist(customer);
    }


}
