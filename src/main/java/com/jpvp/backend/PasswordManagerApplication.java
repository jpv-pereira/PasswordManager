package com.jpvp.backend;

import com.jpvp.backend.Config.DataTest;
import com.jpvp.backend.Config.GenerateSecureKey;
import com.jpvp.backend.Model.User;
import com.jpvp.backend.Persistance.JpaUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PasswordManagerApplication {


	public static void main(String[] args) {
		SpringApplication.run(PasswordManagerApplication.class, args);
	}
}
