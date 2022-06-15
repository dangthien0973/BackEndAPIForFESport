package com.example.ApiDoAn;

import java.sql.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ApiDoAnApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDoAnApplication.class, args);
		
	}
}
