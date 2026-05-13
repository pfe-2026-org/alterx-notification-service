package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.ActionReviewCreatedPayload;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnActionReviewCreatedHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(ActionReviewCreatedPayload payload) {
        UserInfo user = userServiceClient.getUserById(payload.teamLeadId());
        if (user == null) {
            log.error("TeamLead non trouvé: {}", payload.teamLeadId());
            return;
        }
        subscriberManager.upsertSubscriber(
                user.id(), user.email(), user.firstName(), user.lastName()
        );
        notificationSender.send(
                payload.teamLeadId(),
                "action-review-created",
                payload
        );
    }
}