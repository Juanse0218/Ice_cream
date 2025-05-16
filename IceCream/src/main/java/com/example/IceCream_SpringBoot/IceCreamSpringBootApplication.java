package com.example.IceCream_SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.IceCream_SpringBoot.repository")
public class IceCreamSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(IceCreamSpringBootApplication.class, args);
	}

}
