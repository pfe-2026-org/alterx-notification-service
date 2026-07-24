package com.dxc.notification_service.application.notification.handlers;

import com.dxc.notification_service.domain.notification.events.OutsourcingManagerDecisionPayload;
import com.dxc.notification_service.domain.notification.ports.*;
import com.dxc.notification_service.domain.notification.ports.IUserServiceClient.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnOutsourcingManagerDecisionHandler {

    private final INotificationSender notificationSender;
    private final IUserServiceClient userServiceClient;
    private final ISubscriberManager subscriberManager;

    public void handle(OutsourcingManagerDecisionPayload payload) {
        UserInfo changeLead = userServiceClient.getUserById(payload.changeLeadId());
        if (changeLead == null) return;
        subscriberManager.upsertSubscriber(
                changeLead.id(), changeLead.email(), changeLead.firstName(), changeLead.lastName());
        notificationSender.send(
                payload.changeLeadId(), "workflow-outsourcing-manager-decision", payload);
        log.info("Decision {} notifiée: {}", payload.decision(), payload.changeKey());
    }
}