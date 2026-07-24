package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events
        .ScheduleStartedPayload;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports
        .IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnScheduleStartedHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(ScheduleStartedPayload payload) {

        UserInfo user = userServiceClient
                .getUserById(payload.changeManagerId());

        if (user == null) {
            log.error("ChangeManager non trouvé: {}",
                    payload.changeManagerId());
            return;
        }

        subscriberManager.upsertSubscriber(
                user.id(), user.email(),
                user.firstName(), user.lastName()
        );

        notificationSender.send(
                payload.changeManagerId(),
                "schedule-started",
                payload
        );
    }
}