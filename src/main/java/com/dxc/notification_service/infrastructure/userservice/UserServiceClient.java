package com.dxc.notification_service.infrastructure.userservice;

import com.dxc.notification_service.domain.notification.ports.IUserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
//@Component
@RequiredArgsConstructor
public class UserServiceClient implements IUserServiceClient {

    private final RestTemplate restTemplate;

    @Value("${user-service.url}")
    private String userServiceUrl;

    @Override
    public UserInfo getUserById(String userId) {
        try {
            String url = userServiceUrl + "/api/users/" + userId;

            UserServiceResponse response = restTemplate.getForObject(
                    url, UserServiceResponse.class
            );

            if (response == null) {
                log.error("User non trouvé pour id: {}", userId);
                return null;
            }

            return new UserInfo(
                    response.id(),
                    response.email(),
                    response.firstName(),
                    response.lastName()
            );

        } catch (Exception e) {
            log.error("Erreur user-service pour id {}: {}", userId, e.getMessage());
            return null;
        }
    }

    private record UserServiceResponse(
            String id,
            String email,
            String firstName,
            String lastName
    ) {}
}