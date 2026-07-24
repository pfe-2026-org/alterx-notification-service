package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.ActionScheduledPayload;
import com.dxc.notification_service.domain.notification.ports.INotificationSender;
import com.dxc.notification_service.domain.notification.ports.ISubscriberManager;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnActionScheduledHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(ActionScheduledPayload payload) {

        // Récupérer les infos du TeamLead
        UserInfo user = userServiceClient
                .getUserById(payload.teamLeadId());

        if (user == null) {
            log.error("TeamLead non trouvé: {}", payload.teamLeadId());
            return;
        }

        // Enregistrer dans Novu
        subscriberManager.upsertSubscriber(
                user.id(), user.email(),
                user.firstName(), user.lastName()
        );

        // Envoyer la notification
        log.info("Notifying TeamLead {} — action scheduled {}",
                payload.teamLeadId(), payload.actionId());

        notificationSender.send(
                payload.teamLeadId(),
                "workflow-action-scheduled",
                payload
        );
    }
}