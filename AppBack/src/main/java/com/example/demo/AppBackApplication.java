package com.example.demo;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.entities.AppRole;
import com.example.demo.services.AcountService;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@ComponentScan({ "com.example.demo.dao", "com.example.demo.entities", "com.example.demo.services",
		"com.example.demo.web", "com.example.security" })
public class AppBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppBackApplication.class, args);
	}

	@Bean
	CommandLineRunner start(AcountService acountService) {

		return args -> {
			acountService.save(new AppRole(null, "USER"));
			acountService.save(new AppRole(null, "ADMIN"));
			Stream.of("user1", "user2", "admin").forEach(u -> {
				acountService.saveUser(u, "1234", "1234");
			});
			acountService.addRoleToUser("admin", "ADMIN");

		};
	}

	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

}
