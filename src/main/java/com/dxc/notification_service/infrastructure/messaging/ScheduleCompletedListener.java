package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification
        .handlers.OnScheduleCompletedHandler;
import com.dxc.notification_service.domain.notification.events
        .ScheduleCompletedPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleCompletedListener {

    private final OnScheduleCompletedHandler handler;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "schedule.completed.v1",
            groupId = "notification-service-group"
    )
    public void listen(String rawPayload) {
        try {
            ScheduleCompletedPayload payload =
                    objectMapper.readValue(
                            rawPayload,
                            ScheduleCompletedPayload.class);
            handler.handle(payload);
        } catch (Exception e) {
            log.error("Erreur schedule.completed: {}",
                    e.getMessage());
        }
    }
}