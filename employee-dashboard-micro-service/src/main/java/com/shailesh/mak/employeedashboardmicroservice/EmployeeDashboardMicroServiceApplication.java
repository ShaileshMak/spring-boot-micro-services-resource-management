package com.shailesh.mak.employeedashboardmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EmployeeDashboardMicroServiceApplication {

	public static void main(final String[] args) {
		SpringApplication.run(EmployeeDashboardMicroServiceApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
