package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.ChangeRejectedPayload;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnChangeRejectedHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(ChangeRejectedPayload payload) {

        // Notifier ChangeLead
        UserInfo changeLead = userServiceClient
                .getUserById(payload.changeLeadId());
        if (changeLead != null) {
            subscriberManager.upsertSubscriber(
                    changeLead.id(), changeLead.email(),
                    changeLead.firstName(), changeLead.lastName()
            );
            notificationSender.send(
                    payload.changeLeadId(),
                    "change-rejected-lead",
                    payload
            );
        }

        // Notifier ChangeManager
        UserInfo changeManager = userServiceClient
                .getUserById(payload.changeManagerId());
        if (changeManager != null) {
            subscriberManager.upsertSubscriber(
                    changeManager.id(), changeManager.email(),
                    changeManager.firstName(), changeManager.lastName()
            );
            notificationSender.send(
                    payload.changeManagerId(),
                    "change-rejected-manager",
                    payload
            );
        }
    }
}