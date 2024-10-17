package com.example.issuems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
@ComponentScan
@EnableJpaRepositories("com.example.issuems.repo")
public class IssuemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssuemsApplication.class, args);
	}

}
