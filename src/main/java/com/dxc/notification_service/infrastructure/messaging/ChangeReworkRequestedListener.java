package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnChangeReworkRequestedHandler;
import com.dxc.notification_service.domain.notification.events.ChangeReworkRequestedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeReworkRequestedListener {

    private final OnChangeReworkRequestedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "change.rework-requested",
            groupId = "notification-service-group")
    public void listen(String rawPayload) {
        try {
            ChangeReworkRequestedPayload payload = objectMapper.readValue(
                    rawPayload, ChangeReworkRequestedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur change.rework-requested: {}", e.getMessage());
        }
    }
}