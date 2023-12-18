package com.arqui.integrador.mcsvmaintenance.config;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MaintenanceConfig {
    
    @Bean
    @LoadBalanced
	public RestTemplate restTemplateMaintenance() { 
        return new RestTemplate(); 
    } 
}
