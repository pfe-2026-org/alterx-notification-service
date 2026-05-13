package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnActionReviewCreatedHandler;
import com.dxc.notification_service.domain.notification.events.ActionReviewCreatedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionReviewCreatedListener {

    private final OnActionReviewCreatedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "action.review-created",
            groupId = "notification-service-group")
    public void listen(String rawPayload) {
        try {
            ActionReviewCreatedPayload payload = objectMapper.readValue(
                    rawPayload, ActionReviewCreatedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur action.review-created: {}", e.getMessage());
        }
    }
}