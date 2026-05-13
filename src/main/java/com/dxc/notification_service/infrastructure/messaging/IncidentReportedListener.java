package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.OnIncidentReportedHandler;
import com.dxc.notification_service.domain.notification.events.IncidentReportedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IncidentReportedListener {

    private final OnIncidentReportedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "incident.reported",
            groupId = "notification-service-group")
    public void listen(String rawPayload) {
        try {
            IncidentReportedPayload payload = objectMapper.readValue(
                    rawPayload, IncidentReportedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur incident.reported: {}", e.getMessage());
        }
    }
}