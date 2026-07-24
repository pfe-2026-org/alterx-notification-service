package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification
        .handlers.OnScheduleStartedHandler;
import com.dxc.notification_service.domain.notification.events
        .ScheduleStartedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleStartedListener {

    private final OnScheduleStartedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "schedule.started.v1",
            groupId = "notification-service-group"
    )
    public void listen(String rawPayload) {
        try {
            ScheduleStartedPayload payload =
                    objectMapper.readValue(
                            rawPayload,
                            ScheduleStartedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur schedule.started: {}",
                    e.getMessage());
        }
    }
}