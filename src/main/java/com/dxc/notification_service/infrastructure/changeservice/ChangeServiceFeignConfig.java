package com.dxc.notification_service.infrastructure.changeservice;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
public class ChangeServiceFeignConfig {

    @Bean
    public RequestInterceptor changeServiceInterceptor(OAuth2AuthorizedClientManager manager) {
        return new ChangeServiceFeignInterceptor(manager);
    }
}