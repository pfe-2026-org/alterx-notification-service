package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnChangeReviewCreatedHandler;
import com.dxc.notification_service.domain.notification.events.ChangeReviewCreatedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeReviewCreatedListener {

    private final OnChangeReviewCreatedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "change.review-created",
            groupId = "notification-service-group")
    public void listen(String rawPayload) {
        try {
            ChangeReviewCreatedPayload payload = objectMapper.readValue(
                    rawPayload, ChangeReviewCreatedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur change.review-created: {}", e.getMessage());
        }
    }
}