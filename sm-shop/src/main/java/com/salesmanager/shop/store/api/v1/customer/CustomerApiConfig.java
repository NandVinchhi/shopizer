package com.salesmanager.shop.store.api.v1.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerApiConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}