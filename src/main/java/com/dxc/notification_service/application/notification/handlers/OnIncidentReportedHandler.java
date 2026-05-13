package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.IncidentReportedPayload;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnIncidentReportedHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(IncidentReportedPayload payload) {
        UserInfo user = userServiceClient
                .getUserById(payload.incidentManagerId());
        if (user == null) {
            log.error("IncidentManager non trouvé: {}", payload.incidentManagerId());
            return;
        }
        subscriberManager.upsertSubscriber(
                user.id(), user.email(), user.firstName(), user.lastName()
        );
        notificationSender.send(
                payload.incidentManagerId(),
                "incident-reported",
                payload
        );
    }
}