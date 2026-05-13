package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnActionReworkRequestedHandler;
import com.dxc.notification_service.domain.notification.events.ActionReworkRequestedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionReworkRequestedListener {

    private final OnActionReworkRequestedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "action.rework-requested",
            groupId = "notification-service-group")
    public void listen(String rawPayload) {
        try {
            ActionReworkRequestedPayload payload = objectMapper.readValue(
                    rawPayload, ActionReworkRequestedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur action.rework-requested: {}", e.getMessage());
        }
    }
}