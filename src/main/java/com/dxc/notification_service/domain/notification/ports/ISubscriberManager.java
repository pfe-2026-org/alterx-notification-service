package com.dxc.notification_service.domain.notification.ports;

public interface ISubscriberManager {
    void upsertSubscriber(String subscriberId, String email,
                          String firstName, String lastName);
}