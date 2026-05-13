package com.dxc.notification_service.infrastructure.novu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NovuConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}