package com.jpvp.backend;

import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Persistance.JpaUserDao;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JpaUserDaoTests {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JpaUserDao jpaUserDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static boolean setUpDone = false;

    @BeforeEach
    public void setUp() {
        if(!setUpDone) {
            User testUser = new User();
            testUser.setEmail("test@email.com");
            testUser.setUsername("test");
            testUser.setPassword(passwordEncoder.encode("password"));
            testUser.setRole("user");

            jpaUserDao.createUser(testUser);

            setUpDone = true;
        }
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("testCreate@email.com");
        user.setUsername("testCreate");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole("user");

        User createdUser = jpaUserDao.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getPassword(), createdUser.getPassword());
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = jpaUserDao.getAllUsers();

        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertEquals("test@email.com", userList.get(0).getEmail());
        assertEquals("test", userList.get(0).getUsername());
        assertTrue(passwordEncoder.matches("password", userList.get(0).getPassword()));
    }

    @Test
    public void testVerifyExists() {
        assertTrue(jpaUserDao.verifyExists("email", "test@email.com", User.class));
        assertFalse(jpaUserDao.verifyExists("email", "test1@email.com", User.class));

        assertTrue(jpaUserDao.verifyExists("username", "test", User.class));
        assertFalse(jpaUserDao.verifyExists("username", "test1", User.class));
    }

    @Test
    public void testFindByEmail() {
        User user = jpaUserDao.findByEmail("test@email.com");

        assertNotNull(user);
        assertEquals("test@email.com", user.getEmail());
        assertEquals("test", user.getUsername());
    }

    @Test
    @Transactional
    public void testCreateAndGetStoredPassword() {

        User user = new User();
        user.setEmail("testPassword@email.com");
        user.setUsername("testPassword");
        user.setPassword("password");

        StoredPassword storedPassword = new StoredPassword();
        storedPassword.setServiceName("Test Service");
        storedPassword.setPassword("password");

        User createdUser = jpaUserDao.createUser(user);
        jpaUserDao.createStoredPassword(createdUser, storedPassword);

        assertEquals(1, createdUser.getStoredPasswordList().size());

        StoredPassword createdPassword = createdUser.getStoredPasswordList().get(0);

        assertNotNull(createdPassword);
        assertEquals("Test Service", createdPassword.getServiceName());
        assertEquals("password", createdPassword.getPassword());
    }
}
