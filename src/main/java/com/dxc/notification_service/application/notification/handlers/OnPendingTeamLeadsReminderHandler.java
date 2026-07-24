package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.PendingTeamLeadsReminderPayload;
import com.dxc.notification_service.domain.notification.events.PendingTeamLeadsReminderPayload.PendingAssignmentItem;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnPendingTeamLeadsReminderHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(PendingTeamLeadsReminderPayload payload) {
        if (payload.pendingAssignments() == null) return;

        for (PendingAssignmentItem item : payload.pendingAssignments()) {
            UserInfo teamLead = userServiceClient.getUserById(item.teamLeadId());
            if (teamLead == null) {
                log.warn("TeamLead non trouvé: {}", item.teamLeadId());
                continue;
            }
            subscriberManager.upsertSubscriber(
                    teamLead.id(), teamLead.email(), teamLead.firstName(), teamLead.lastName());
            notificationSender.send(
                    item.teamLeadId(), "workflow-pending-team-leads-reminder", payload);
        }

        log.info("Rappel envoyé pour {} action(s) en attente sur {}",
                payload.pendingAssignments().size(), payload.changeKey());
    }
}