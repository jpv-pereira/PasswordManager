package com.jpvp.backend.Controller;

import com.jpvp.backend.Config.JwtResponse;
import com.jpvp.backend.Config.JwtUtils;
import com.jpvp.backend.Exception.IncorrectPasswordException;
import com.jpvp.backend.Exception.TokenValidationException;
import com.jpvp.backend.Exception.UserNotFoundException;
import com.jpvp.backend.Model.LoginRequest;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.ResponseEntity;
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
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    A simple test mapping to see if I haven't royally screwed up and that the server is actually able to respond with 200
     */
    @GetMapping
    public String testMapping() {
        System.out.println("testedtested");
        return "Test";
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user == null) {
            throw new UserNotFoundException();
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
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

    @GetMapping(value = "/storedpasswords")
    public List<StoredPassword> getStoredPasswords (User user) {
        return userService.findByUserName(user.getUsername()).getStoredPasswordList();
    }

    @PostMapping(value = "/createStoredPassword")
    public void createStoredPassword(@RequestHeader("Authorization") String token, @RequestParam StoredPassword storedPassword) {
        if (jwtUtils.validateToken(token)) {
            String username = jwtUtils.extractUsername(token);
            System.out.println("Created password success");
            userService.createStoredPassword(username, storedPassword);
        } else {
            throw new TokenValidationException();
        }
    }
}
