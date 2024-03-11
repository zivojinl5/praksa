package com.example.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.backend.core.req_model.RegisterRequest;
import com.example.backend.core.service_Implementation.AuthenticationServiceImpl;

import static com.example.backend.enums.Role.ADMIN;
import static com.example.backend.enums.UserStatus.ACTIVE;;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationServiceImpl service) {
		return args -> {
			var admin = RegisterRequest.builder()
					.name("Admin")
					.userName("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.userStatus(ACTIVE)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

		};
	}
}
