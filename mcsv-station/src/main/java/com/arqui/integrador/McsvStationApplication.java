package com.arqui.integrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class McsvStationApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsvStationApplication.class, args);
	}

}
