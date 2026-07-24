package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.TeamLeadAssignmentAutoRejectedPayload;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnTeamLeadAssignmentAutoRejectedHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(TeamLeadAssignmentAutoRejectedPayload payload) {
        UserInfo changeLead = userServiceClient.getUserById(payload.changeLeadId());
        if (changeLead == null) {
            log.warn("ChangeLead non trouvé: {}", payload.changeLeadId());
            return;
        }

        subscriberManager.upsertSubscriber(
                changeLead.id(), changeLead.email(), changeLead.firstName(), changeLead.lastName());
        notificationSender.send(
                payload.changeLeadId(), "workflow-team-lead-auto-rejected", payload);

        log.info("Assignment auto-rejeté notifié: {} — action {}",
                payload.changeKey(), payload.actionTitle());
    }
}