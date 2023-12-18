package com.arqui.integrador.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ScooterConfig {
	@Bean
	@LoadBalanced
	public RestTemplate restTemplateScooter() { 
        return new RestTemplate(); 
    }
}
