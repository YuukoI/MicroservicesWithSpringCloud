package com.motorbike.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MotorbikeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotorbikeServiceApplication.class, args);
	}

}
