package com.dxc.notification_service.infrastructure.userservice;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
public class UserServiceFeignConfig {
    @Bean
    public RequestInterceptor userServiceInterceptor(OAuth2AuthorizedClientManager m) {
        return new UserServiceFeignInterceptor(m);
    }
}