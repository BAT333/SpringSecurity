package com.example.userSeguranca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserSegurancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSegurancaApplication.class, args);
	}

}
