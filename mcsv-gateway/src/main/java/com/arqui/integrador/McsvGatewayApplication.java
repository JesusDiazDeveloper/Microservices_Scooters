package com.arqui.integrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class McsvGatewayApplication {

	public static void main(final String[] args) {
	    SpringApplication.run(McsvGatewayApplication.class, args);
	}
}
