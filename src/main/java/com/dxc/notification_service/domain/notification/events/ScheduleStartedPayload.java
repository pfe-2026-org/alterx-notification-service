package com.dxc.notification_service.domain.notification.events;

public record ScheduleStartedPayload(
        String changeKey,
        String changeManagerId  // destinataire
) {}