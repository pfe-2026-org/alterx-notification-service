package com.dxc.notification_service.domain.notification.events;

public record ScheduleDelayedPayload(
        String changeKey,
        String changeManagerId,  // destinataire
        int daysDelayed
) {}