package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.ChangeExecutionStartedPayload;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnChangeExecutionStartedHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(ChangeExecutionStartedPayload payload) {
        notifyUser(payload.changeLeadId(), payload);
        if (payload.teamLeadIds() != null) {
            payload.teamLeadIds().forEach(id -> notifyUser(id, payload));
        }
        log.info("Exécution démarrée notifiée: {}", payload.changeKey());
    }

    private void notifyUser(String userId, ChangeExecutionStartedPayload payload) {
        if (userId == null) return;
        UserInfo user = userServiceClient.getUserById(userId);
        if (user == null) return;
        subscriberManager.upsertSubscriber(user.id(), user.email(), user.firstName(), user.lastName());
        notificationSender.send(userId, "workflow-change-execution-started", payload);
    }
}