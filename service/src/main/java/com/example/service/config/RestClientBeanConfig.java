package com.example.service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Configuration
public class RestClientBeanConfig {
    @Bean
    @Qualifier("custom-base-api")
    public BaseApiCall getBaseApiCall() {
        var restTemplate = new RestTemplate();
        return new BaseApiCall(restTemplate);
    }
}
