package com.dxc.notification_service.infrastructure.userservice;

import com.dxc.notification_service.domain.notification.ports.IUserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceClient implements IUserServiceClient {

    private final UserServiceFeignClient userServiceFeignClient;

    @Override
    public UserInfo getUserById(String userId) {
        try {
            UUID id = UUID.fromString(userId);
            var response = userServiceFeignClient.getUserById(id);
            if (response == null) {
                log.error("User non trouvé pour id: {}", userId);
                return null;
            }
            return new UserInfo(
                    response.id().toString(),
                    response.email(),
                    response.firstName(),
                    response.lastName()
            );
        } catch (IllegalArgumentException e) {
            log.error("userId invalide (pas un UUID): {}", userId);
            return null;
        } catch (Exception e) {
            log.error("Erreur user-service pour id {}: {}", userId, e.getMessage());
            return null;
        }
    }

    @Override
    public String getCampusIdByUserId(String userId) {
        try {
            UUID id = UUID.fromString(userId);
            var response = userServiceFeignClient.getHierarchy(id);
            if (response == null || response.hierarchy() == null) return null;
            return response.hierarchy().campusId();
        } catch (IllegalArgumentException e) {
            log.error("userId invalide (pas un UUID): {}", userId);
            return null;
        } catch (Exception e) {
            log.error("Erreur user-service hierarchy {}: {}", userId, e.getMessage());
            return null;
        }
    }
}