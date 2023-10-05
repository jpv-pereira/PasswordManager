package com.jpvp.backend.Controller;

import com.jpvp.backend.Config.JwtResponse;
import com.jpvp.backend.Exception.TokenValidationException;
import com.jpvp.backend.Service.JwtTokenService;
import com.jpvp.backend.Exception.EmailNotFoundException;
import com.jpvp.backend.Exception.IncorrectPasswordException;
import com.jpvp.backend.Exception.UserNotFoundException;
import com.jpvp.backend.Model.LoginRequest;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Model.StoredPassword;
import com.jpvp.backend.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    A simple test mapping to see if I haven't royally screwed up and that the server is actually able to respond with 200
     */
    @GetMapping("/test")
    public String testMapping() {
        return "Test";
    }

    @PostMapping(value = "/login")
    public JwtResponse loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        User user = userService.findByEmail(loginRequest.getEmail());
        System.out.println(request.getRemoteAddr());

        if (user == null) {
            throw new UserNotFoundException();
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        String token = jwtTokenService.generateToken(user.getEmail(), request);

        return new JwtResponse(token);
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping(value = "/list")
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @GetMapping(value = "/find")
    public User findByUsername (String username) {
        return userService.findByUsername(username);
    }

    @GetMapping(value = "/stored-passwords")
    public List<StoredPassword> getStoredPasswords (@RequestHeader("Authorization") String token) {
        String email = jwtTokenService.extractEmail(jwtTokenService.extractToken(token));
        if (!email.isEmpty()) {
            return userService.getStoredPasswords(email);
        } else throw new EmailNotFoundException();
    }

    @PostMapping(value = "/stored-passwords")
    public void createStoredPassword(@RequestHeader("Authorization") String token, @RequestBody StoredPassword storedPassword) {
        String email = jwtTokenService.extractEmail(jwtTokenService.extractToken(token));
        if (!email.isEmpty()) {
            userService.createStoredPassword(email, storedPassword);
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
