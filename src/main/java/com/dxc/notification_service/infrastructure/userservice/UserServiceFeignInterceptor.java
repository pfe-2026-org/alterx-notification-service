package com.dxc.notification_service.infrastructure.userservice;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.*;

public class UserServiceFeignInterceptor implements RequestInterceptor {
    private static final String REGISTRATION_ID = "notification-service-user";
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public UserServiceFeignInterceptor(OAuth2AuthorizedClientManager m) {
        this.authorizedClientManager = m;
    }

    @Override
    public void apply(RequestTemplate template) {
        var req = OAuth2AuthorizeRequest.withClientRegistrationId(REGISTRATION_ID).principal(REGISTRATION_ID).build();
        var client = authorizedClientManager.authorize(req);
        template.header("Authorization", "Bearer " + client.getAccessToken().getTokenValue());
    }
}