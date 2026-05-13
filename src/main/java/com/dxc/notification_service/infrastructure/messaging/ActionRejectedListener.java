package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnActionRejectedHandler;
import com.dxc.notification_service.domain.notification.events.ActionRejectedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionRejectedListener {

    private final OnActionRejectedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "action.rejected",
            groupId = "notification-service-group")
    public void listen(String rawPayload) {
        try {
            ActionRejectedPayload payload = objectMapper.readValue(
                    rawPayload, ActionRejectedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur action.rejected: {}", e.getMessage());
        }
    }
}