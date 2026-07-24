package com.dxc.notification_service.domain.notification.events;

public record ActionScheduledPayload(
        String changeKey,
        String actionId,
        String teamLeadId,
        String startDate,
        String dueDate
) {}