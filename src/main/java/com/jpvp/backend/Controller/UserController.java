package com.jpvp.backend.Controller;

import com.jpvp.backend.Model.User;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    JwtUtils
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    /*
    A simple test mapping to see if I haven't royally screwed up and that the server is actually able to respond with 200
     */
    @GetMapping
    public String testMapping() {
        System.out.println("testedtested");
        return "Test";
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody String email, String password) {
    }

    @PostMapping(value = "/register")
    public User createUser(@RequestBody User user) {

        System.out.println("Tried to register ");
        return userService.createUser(user);
    }

    @GetMapping(value = "/all")
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @GetMapping(value = "/findByUserName")
    public User findByUserMame (String userName) {
        return userService.findByUserName(userName);
    }

    @GetMapping(value = "/findByUserName")
    public List<StoredPassword> getStoredPasswords (User user) {
        return userService.findByUserName(userName);
    }

    @PostMapping(value = "/createStoredPassword")
    public void createStoredPassword(User user, StoredPassword storedPassword) {
        userService.createStoredPassword(user, storedPassword);
    }
}
