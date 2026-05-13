package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.ChangeReviewCreatedPayload;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnChangeReviewCreatedHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(ChangeReviewCreatedPayload payload) {
        UserInfo user = userServiceClient.getUserById(payload.supplierManagerId());
        if (user == null) {
            log.error("SupplierManager non trouvé: {}", payload.supplierManagerId());
            return;
        }
        subscriberManager.upsertSubscriber(
                user.id(), user.email(), user.firstName(), user.lastName()
        );
        notificationSender.send(
                payload.supplierManagerId(),
                "change-review-created",
                payload
        );
    }
}