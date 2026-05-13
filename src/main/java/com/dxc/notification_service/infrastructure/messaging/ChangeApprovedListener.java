package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnChangeApprovedHandler;
import com.dxc.notification_service.domain.notification.events.ChangeApprovedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeApprovedListener {

    private final OnChangeApprovedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "change.approved",
            groupId = "notification-service-group")
    public void listen(String rawPayload) {
        try {
            ChangeApprovedPayload payload = objectMapper.readValue(
                    rawPayload, ChangeApprovedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur change.approved: {}", e.getMessage());
        }
    }
}