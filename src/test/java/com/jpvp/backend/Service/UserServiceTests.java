package com.jpvp.backend.Service;

import com.jpvp.backend.Exception.EmailTakenException;
import com.jpvp.backend.Exception.PasswordDecryptException;
import com.jpvp.backend.Exception.UsernameTakenException;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    private static boolean setUpDone = false;

    @BeforeEach
    public void setUp() {
        if (!setUpDone) {
            User testUser = new User();
            testUser.setEmail("test@email.com");
            testUser.setUsername("testUser");
            testUser.setPassword("123");
            testUser.setRole("user");

            userService.createUser(testUser);

            //Mockito.when(userService.findByUsername(testUser.getUsername())).thenReturn(testUser);
            setUpDone = true;
        }
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testCreateUser() {
        User createUser = new User();
        createUser.setEmail("createUser@email.com");
        createUser.setUsername("createUser");
        createUser.setPassword("password");
        createUser.setRole("user");

        User savedUser = new User();
        savedUser.setEmail("createUser@email.com");
        savedUser.setUsername("createUser");
        savedUser.setRole("user");
        savedUser.setId(2L); // The id has to be 2 since a user is already created in setUp

        User createdUser = userService.createUser(createUser);

        assertNotNull(createdUser);
        assertEquals(savedUser.getId(), createdUser.getId());
        assertEquals(savedUser.getEmail(), createdUser.getEmail());
        assertEquals(savedUser.getUsername(), createdUser.getUsername());

        /*
        Test EmailTakenException
        */

        User testEmailTakenException = new User();
        testEmailTakenException.setEmail("createUser@email.com");

        assertThrows(EmailTakenException.class, () -> userService.createUser(testEmailTakenException));

        /*
        Test UsernameTakenException
        */

        User testUsernameTakenException = new User();
        testUsernameTakenException.setUsername("createUser");

        assertThrows(UsernameTakenException.class, () -> userService.createUser(testUsernameTakenException));

        /*
        Test PasswordDecryptException
        */

        User testPasswordDecryptException = new User();
        testPasswordDecryptException.setEmail("testPasswordDecrypt@email.com");
        testPasswordDecryptException.setUsername("testPasswordDecrypt");
        testPasswordDecryptException.setPassword("");

        assertThrows(PasswordDecryptException.class, () -> userService.createUser(testPasswordDecryptException));

        testPasswordDecryptException.setPassword(null);

        assertThrows(PasswordDecryptException.class, () -> userService.createUser(testPasswordDecryptException));
    }

    @Test
    public void testListUsers() {
        List<User> userList = userService.listUsers();
        assertEquals(1, userList.size());
    }

    @Test
    public void testFindByUsername() {
        String username = "testUser";
        User user = userService.findByUsername(username);

        assertNotNull(user);
        assertEquals(user.getUsername(), username);
    }

    @Test
    @Transactional
    public void testCreateAndGetStoredPassword() {
        StoredPassword storedPassword = new StoredPassword();
        storedPassword.setServiceName("Test Service");
        storedPassword.setPassword("password");

        userService.createStoredPassword("test@email.com", storedPassword);
        List<StoredPassword> storedPasswordList = userService.getStoredPasswords("test@email.com");

        assertEquals(1, storedPasswordList.size());

        assertEquals("Test Service", storedPasswordList.get(0).getServiceName());
        assertEquals("password", storedPasswordList.get(0).getPassword());


    }
}
