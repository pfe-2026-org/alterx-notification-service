package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.ChangeCompletedPayload;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnChangeCompletedHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final IOrgServiceClient orgServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(ChangeCompletedPayload payload) {
        notifyUser(payload.changeLeadId(), payload);

        if (payload.teamLeadIds() != null) {
            payload.teamLeadIds().forEach(id -> notifyUser(id, payload));
        }

        // Avant: on résolvait le campus via changeLeadId → user-service
        // Maintenant: le campus est DÉJÀ dans le payload, un appel en moins
        if (payload.recipientRoles() != null && payload.changeClientCampusId() != null) {
            payload.recipientRoles().forEach(role ->
                    orgServiceClient.getUserIdsByCampusAndRole(payload.changeClientCampusId(), role)
                            .forEach(id -> notifyUser(id, payload))
            );
        }

        log.info("Change complété notifié: {}", payload.changeKey());
    }

    private void notifyUser(String userId, ChangeCompletedPayload payload) {
        if (userId == null) return;
        UserInfo user = userServiceClient.getUserById(userId);
        if (user == null) return;
        subscriberManager.upsertSubscriber(user.id(), user.email(), user.firstName(), user.lastName());
        notificationSender.send(userId, "workflow-change-completed", payload);
    }
}