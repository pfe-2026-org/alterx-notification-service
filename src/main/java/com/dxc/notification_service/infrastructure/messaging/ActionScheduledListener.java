package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnActionScheduledHandler;
import com.dxc.notification_service.domain.notification.events.ActionScheduledPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionScheduledListener {

    private final OnActionScheduledHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "action.scheduled.v1",
            groupId = "notification-service-group"
    )
    public void listen(String rawPayload) {
        try {
            ActionScheduledPayload payload = objectMapper.readValue(
                    rawPayload, ActionScheduledPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur action.scheduled: {}", e.getMessage());
        }
    }
}