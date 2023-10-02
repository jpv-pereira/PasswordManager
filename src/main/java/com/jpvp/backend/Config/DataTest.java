package com.jpvp.backend.Config;

import com.jpvp.backend.Model.User;
import com.jpvp.backend.Persistance.JpaUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class DataTest implements ApplicationRunner {
    @Autowired
    private JpaUserDao jpaUserDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user1 = new User();
        user1.setUsername("joao");
        user1.setEmail("joao@email.com");
        user1.setPassword(bCryptPasswordEncoder.encode("password1"));
        user1.setRole("user");

        jpaUserDao.createUser(user1);
    }
}
