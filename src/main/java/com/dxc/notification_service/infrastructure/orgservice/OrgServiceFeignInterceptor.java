package com.dxc.notification_service.infrastructure.orgservice;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public class OrgServiceFeignInterceptor implements RequestInterceptor {

    private static final String REGISTRATION_ID = "notification-service-org";
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public OrgServiceFeignInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public void apply(RequestTemplate template) {
        var request = OAuth2AuthorizeRequest.withClientRegistrationId(REGISTRATION_ID)
                .principal(REGISTRATION_ID).build();
        var client = authorizedClientManager.authorize(request);
        template.header("Authorization", "Bearer " + client.getAccessToken().getTokenValue());
    }
}