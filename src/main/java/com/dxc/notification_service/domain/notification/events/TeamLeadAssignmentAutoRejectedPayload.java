package com.dxc.notification_service.domain.notification.events;

public record TeamLeadAssignmentAutoRejectedPayload(
        String eventId, String changeId, String changeKey, String changeTitle,
        String changeClientCampusId, String changeLeadId,
        String actionId, String actionTitle, String teamLeadId,
        String reason, String occurredOn
) {}
