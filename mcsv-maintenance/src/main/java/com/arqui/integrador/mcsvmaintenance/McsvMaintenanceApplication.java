package com.arqui.integrador.mcsvmaintenance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class McsvMaintenanceApplication {
	public static void main(String[] args) {
		SpringApplication.run(McsvMaintenanceApplication.class, args);
	}
}
