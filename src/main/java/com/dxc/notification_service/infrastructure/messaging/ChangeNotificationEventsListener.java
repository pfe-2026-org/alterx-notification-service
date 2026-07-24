package com.dxc.notification_service.infrastructure.messaging;

import com.dxc.notification_service.application.notification.handlers.*;
import com.dxc.notification_service.domain.notification.events.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeNotificationEventsListener {

    private final ObjectMapper objectMapper;

    private final OnTeamLeadDecisionRecordedHandler teamLeadDecisionHandler;
    private final OnTeamLeadAssignmentAutoRejectedHandler autoRejectedHandler;
    private final OnPendingTeamLeadsReminderHandler reminderHandler;
    private final OnOutsourcingManagerDecisionHandler outsourcingDecisionHandler;
    private final OnChangeScheduledHandler changeScheduledHandler;
    private final OnChangeExecutionStartedHandler executionStartedHandler;
    private final OnActionCompletedHandler actionCompletedHandler;
    private final OnChangeCompletedHandler changeCompletedHandler;
    private final OnChangeCancelledHandler changeCancelledHandler;

    @KafkaListener(
            topics = "change.notification-events.v1",
            groupId = "notification-service-group"
    )
    public void onNotificationEvent(
            String rawPayload,
            @Header("eventType") byte[] eventTypeBytes) {

        String eventType = new String(eventTypeBytes, StandardCharsets.UTF_8);

        try {
            switch (eventType) {
                case "TEAM_LEAD_DECISION_RECORDED" -> {
                    var payload = objectMapper.readValue(rawPayload, TeamLeadDecisionRecordedPayload.class);
                    teamLeadDecisionHandler.handle(payload);
                }
                case "TEAM_LEAD_ASSIGNMENT_AUTO_REJECTED" -> {
                    var payload = objectMapper.readValue(rawPayload, TeamLeadAssignmentAutoRejectedPayload.class);
                    autoRejectedHandler.handle(payload);
                }
                case "PENDING_TEAM_LEADS_REMINDER_REQUESTED" -> {
                    var payload = objectMapper.readValue(rawPayload, PendingTeamLeadsReminderPayload.class);
                    reminderHandler.handle(payload);
                }
                case "OUTSOURCING_MANAGER_DECISION_RECORDED" -> {
                    var payload = objectMapper.readValue(rawPayload, OutsourcingManagerDecisionPayload.class);
                    outsourcingDecisionHandler.handle(payload);
                }
                case "CHANGE_SCHEDULED" -> {
                    var payload = objectMapper.readValue(rawPayload, ChangeScheduledPayload.class);
                    changeScheduledHandler.handle(payload);
                }
                case "CHANGE_EXECUTION_STARTED" -> {
                    var payload = objectMapper.readValue(rawPayload, ChangeExecutionStartedPayload.class);
                    executionStartedHandler.handle(payload);
                }
                case "ACTION_COMPLETED" -> {
                    var payload = objectMapper.readValue(rawPayload, ActionCompletedPayload.class);
                    actionCompletedHandler.handle(payload);
                }
                case "CHANGE_COMPLETED" -> {
                    var payload = objectMapper.readValue(rawPayload, ChangeCompletedPayload.class);
                    changeCompletedHandler.handle(payload);
                }
                case "CHANGE_CANCELLED" -> {
                    var payload = objectMapper.readValue(rawPayload, ChangeCancelledPayload.class);
                    changeCancelledHandler.handle(payload);
                }
                default -> log.warn("eventType inconnu, ignoré: {}", eventType);
            }
        } catch (Exception e) {
            log.error("Erreur traitement notification-event [{}]: {}", eventType, e.getMessage());
        }
    }
}