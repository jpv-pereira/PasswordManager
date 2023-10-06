package com.jpvp.backend.Persistance.AuthToken;

import com.jpvp.backend.Model.AuthToken;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
    AuthToken findByToken(String token);
    boolean existsByToken(String token);
    List<AuthToken> findAllByUserId(Long id);

    @Transactional
    void deleteByToken(String token);
}
