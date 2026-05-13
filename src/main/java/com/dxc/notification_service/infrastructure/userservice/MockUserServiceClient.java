package com.dxc.notification_service.infrastructure.userservice;

import com.dxc.notification_service.domain.notification.ports.IUserServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Primary // actif seulement en mode test
public class MockUserServiceClient implements IUserServiceClient {

    @Override
    public UserInfo getUserById(String userId) {
        log.info("MOCK — getUserById appelé pour: {}", userId);

        // Retourne des données fictives selon l'ID
        return switch (userId) {
            case "karim-id" -> new UserInfo(
                    "karim-id",
                    "karim@test.com",
                    "Karim",
                    "Benali"
            );
            case "mohamed-id" -> new UserInfo(
                    "mohamed-id",
                    "mohamed@test.com",
                    "Mohamed",
                    "Alami"
            );
            case "supplier-id" -> new UserInfo(
                    "supplier-id",
                    "supplier@test.com",
                    "karim",
                    "bozu"
            );
            case "changelead-id" -> new UserInfo(
                    "changelead-id",
                    "changelead@test.com",
                    "Ahmed",
                    "Tazi"
            );
            case "changemanager-id" -> new UserInfo(
                    "changemanager-id",
                    "changemanager@test.com",
                    "Sara",
                    "Idrissi"
            );
            default -> new UserInfo(
                    userId,
                    "test@test.com",
                    "Test",
                    "User"
            );
        };
    }
}