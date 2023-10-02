package com.jpvp.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpvp.backend.Controller.UserController;
import com.jpvp.backend.Model.LoginRequest;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Service.UserService;
import com.jpvp.backend.Util.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    //A valid token for testing
    private String token;

    @BeforeEach
    public void setup() {
        User testUser = new User();
        testUser.setEmail("test@email.com");
        testUser.setUsername("testUser");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRole("user");

        token = jwtUtils.generateToken(testUser.getUsername());

        Mockito.when(userService.findByEmail(testUser.getEmail())).thenReturn(testUser);

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
