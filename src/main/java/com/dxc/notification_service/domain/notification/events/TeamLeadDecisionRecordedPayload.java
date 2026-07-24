package com.dxc.notification_service.domain.notification.events;

// TeamLeadDecisionRecordedPayload.java
public record TeamLeadDecisionRecordedPayload(
        String eventId, String changeId, String changeKey, String changeTitle,
        String changeClientCampusId, String changeLeadId,
        String actionId, String actionTitle, String assignmentId,
        String resultingActionStatus, String teamLeadId,
        String decision, String justification, String occurredOn
) {}
