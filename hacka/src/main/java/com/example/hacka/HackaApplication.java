package com.example.hacka;

import com.example.hacka.User.Role;
import com.example.hacka.auth.AuthenticationService;
import com.example.hacka.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HackaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService service) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@gmail.com")
					.password("password")
					.age(25)
					.sex("Male")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin Token: " + service.register(admin).getToken());
		};
	}
}
