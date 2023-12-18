package com.arqui.integrador.mcsvadministrator.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AdministratorConfig {
    
    @Bean
    @LoadBalanced
	public RestTemplate restTemplateAdministrator() { 
        return new RestTemplate();
}
}