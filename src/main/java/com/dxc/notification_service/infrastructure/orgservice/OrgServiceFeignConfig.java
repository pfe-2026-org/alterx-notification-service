package com.dxc.notification_service.infrastructure.orgservice;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
public class OrgServiceFeignConfig {

    @Bean
    public RequestInterceptor orgServiceInterceptor(OAuth2AuthorizedClientManager manager) {
        return new OrgServiceFeignInterceptor(manager);
    }
}