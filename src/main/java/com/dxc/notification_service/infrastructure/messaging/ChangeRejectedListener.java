package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnChangeRejectedHandler;
import com.dxc.notification_service.domain.notification.events.ChangeRejectedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeRejectedListener {

    private final OnChangeRejectedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "change.rejected",
            groupId = "notification-service-group")
    public void listen(String rawPayload) {
        try {
            ChangeRejectedPayload payload = objectMapper.readValue(
                    rawPayload, ChangeRejectedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur change.rejected: {}", e.getMessage());
        }
    }
}