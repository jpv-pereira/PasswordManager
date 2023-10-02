package com.jpvp.backend.Persistance;

import com.jpvp.backend.Model.User;
import com.jpvp.backend.Model.StoredPassword;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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
    public User createUser(User user) {
        User managedClient = entityManager.merge(user);
        entityManager.persist(managedClient);
        return managedClient;
    }

    @Override
    public User findByUsername(String userName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);

        Predicate usernamePredicate = criteriaBuilder.equal(userRoot.get("username"), userName);

        criteriaQuery.where(usernamePredicate);


        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);

        Predicate usernamePredicate = criteriaBuilder.equal(userRoot.get("email"), email);

        criteriaQuery.where(usernamePredicate);

        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> boolean verifyExists(String rowName, String verifyString, Class<T> type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<T> userRoot = criteriaQuery.from(type);
        criteriaQuery.select(criteriaBuilder.count(userRoot));

        Predicate existsPredicate = criteriaBuilder.equal(userRoot.get(rowName), verifyString);

        criteriaQuery.where(existsPredicate);

        TypedQuery<Long> customerTypedQuery = entityManager.createQuery(criteriaQuery);

        return customerTypedQuery.getSingleResult() > 0;
    }

    @Override
    public void createStoredPassword(User user, StoredPassword storedPassword) {
        List<StoredPassword> storedPasswordList = user.getStoredPasswordList();
        storedPasswordList.add(storedPassword);

        storedPassword.setUser(user);

        entityManager.persist(user);
    }

    @Override
    public List<StoredPassword> getStoredPasswords(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StoredPassword> criteriaQuery = criteriaBuilder.createQuery(StoredPassword.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        Join<User, StoredPassword> storedPasswordJoin = userRoot.join("storedPasswordList");

        Predicate userNamePredicate = criteriaBuilder.equal(userRoot.get("email"), email);

        criteriaQuery.select(storedPasswordJoin);
        criteriaQuery.where(userNamePredicate);

        try {
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            return null;
        }
    }


}
