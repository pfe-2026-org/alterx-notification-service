package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnActionAutoRejectedHandler;
import com.dxc.notification_service.domain.notification.events.ActionAutoRejectedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionAutoRejectedListener {

    private final OnActionAutoRejectedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "action.auto-rejected",
            groupId = "notification-service-group")
    public void listen(String rawPayload) {
        try {
            ActionAutoRejectedPayload payload = objectMapper.readValue(
                    rawPayload, ActionAutoRejectedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur action.auto-rejected: {}", e.getMessage());
        }
    }
}