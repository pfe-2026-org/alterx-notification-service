package com.dxc.notification_service.domain.notification.ports;

public interface INotificationSender {
    void send(String recipientId, String templateId, Object payload);
}