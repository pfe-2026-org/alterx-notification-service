package com.dxc.notification_service.domain.notification.events;

public record ActionRejectedPayload(
        String changeKey,
        String actionId,
        String justification,
        String changeLeadId
) {}