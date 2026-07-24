package com.dxc.notification_service.domain.notification.events;

public record ScheduleCompletedPayload(
        String changeKey,
        String changeManagerId,  // destinataire 1
        String changeLeadId,     // destinataire 2
        String actualDuration
) {}