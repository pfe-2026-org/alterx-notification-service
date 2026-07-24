package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification
        .handlers.OnScheduleDelayedHandler;
import com.dxc.notification_service.domain.notification.events
        .ScheduleDelayedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleDelayedListener {

    private final OnScheduleDelayedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "schedule.delayed.v1",
            groupId = "notification-service-group"
    )
    public void listen(String rawPayload) {
        try {
            ScheduleDelayedPayload payload =
                    objectMapper.readValue(
                            rawPayload,
                            ScheduleDelayedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur schedule.delayed: {}",
                    e.getMessage());
        }
    }
}