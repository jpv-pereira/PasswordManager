package com.jpvp.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpvp.backend.Model.LoginRequest;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Service.Token.AuthTokenManagerService;
import com.jpvp.backend.Service.UserService;
import com.jpvp.backend.Service.Token.JwtTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback(true)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private AuthTokenManagerService authTokenManagerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenService jwtTokenService;

    //A valid token for testing
    private String token;

    @BeforeEach
    public void setup() {
        User testUser = new User();
        testUser.setEmail("test@email.com");
        testUser.setUsername("testUser");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRole("user");

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getLocalAddr()).thenReturn("11.11.11.111");

        token = jwtTokenService.generateToken(testUser.getUsername(), mockRequest);
        authTokenManagerService.storeToken(token, 1L, jwtTokenService.getClaimFromToken(token, Claims::getExpiration));


        when(userService.findByEmail(testUser.getEmail())).thenReturn(testUser);

    }

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(userService);
    }

    @Test
    public void testRegister() throws Exception {
        User newUser = new User();
        newUser.setEmail("new@user.com");
        newUser.setUsername("newUser");
        newUser.setPassword("password");


        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void testListUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/list")
                .header("Authorization", "Bearer " + token)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@email.com");
        loginRequest.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetStoredPasswords() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/users/stored-passwords")
                .header("Authorization", "Bearer " + token)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateStoredPassword() throws Exception {
        StoredPassword storedPassword = new StoredPassword();
        storedPassword.setServiceName("Test Service");
        storedPassword.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/users/stored-passwords")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(storedPassword))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
