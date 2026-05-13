package com.dxc.notification_service.domain.notification.ports;

public interface IUserServiceClient {

    UserInfo getUserById(String userId);

    record UserInfo(
            String id,
            String email,
            String firstName,
            String lastName
    ) {}
}