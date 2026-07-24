package com.dxc.notification_service.infrastructure.userservice;

import com.dxc.notification_service.domain.notification.ports.IUserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceClient implements IUserServiceClient {

    private final UserServiceFeignClient userServiceFeignClient;

    @Override
    public UserInfo getUserById(String userId) {
        try {
            var response = userServiceFeignClient.getUserById(userId);
            if (response == null) {
                log.error("User non trouvé pour id: {}", userId);
                return null;
            }
            return new UserInfo(response.id(), response.email(), response.firstName(), response.lastName());
        } catch (Exception e) {
            log.error("Erreur user-service pour id {}: {}", userId, e.getMessage());
            return null;
        }
    }

    @Override
    public String getCampusIdByUserId(String userId) {
        try {
            var response = userServiceFeignClient.getHierarchy(userId);
            return response != null ? response.campusId() : null;
        } catch (Exception e) {
            log.error("Erreur user-service hierarchy {}: {}", userId, e.getMessage());
            return null;
        }
    }
}