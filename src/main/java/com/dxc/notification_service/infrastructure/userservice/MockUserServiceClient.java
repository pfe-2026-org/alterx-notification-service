package com.dxc.notification_service.infrastructure.userservice;

import com.dxc.notification_service.domain.notification.ports.IUserServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Primary
public class MockUserServiceClient implements IUserServiceClient {

    @Override
    public UserInfo getUserById(String userId) {
        log.info("MOCK — getUserById pour: {}", userId);
        return switch (userId) {
            case "karim-id" -> new UserInfo("karim-id", "karim@test.com", "Karim", "Benali");
            case "sara-id" -> new UserInfo("sara-id", "sara@test.com", "Sara", "Alami");
            case "ali-id" -> new UserInfo("ali-id", "ali@test.com", "Ali", "Tazi");
            case "supplier-id" -> new UserInfo("supplier-id", "supplier@test.com", "Mohamed", "Supplier");
            case "changelead-id" -> new UserInfo("changelead-id", "changelead@test.com", "Ahmed", "ChangeLead");
            case "changemanager-id" -> new UserInfo("changemanager-id", "changemanager@test.com", "Youssef", "Manager");
            case "incidentmanager-id" -> new UserInfo("incidentmanager-id", "incident@test.com", "Fatima", "IncidentManager");
            default -> new UserInfo(userId, "test@test.com", "Test", "User");
        };
    }

    @Override
    public String getCampusIdByUserId(String userId) {
        log.info("MOCK — getCampusIdByUserId pour: {}", userId);
        return "campus-casablanca";
    }
}