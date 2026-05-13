package com.dxc.notification_service.domain.notification.events;

public record ChangeReworkRequestedPayload(
        String changeKey,
        String justification,
        String changeLeadId,
        String changeManagerId
) {}