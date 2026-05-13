package com.dxc.notification_service.domain.notification.events;

public record ActionReworkRequestedPayload(
        String changeKey,
        String actionId,
        String justification,
        String changeLeadId
) {}