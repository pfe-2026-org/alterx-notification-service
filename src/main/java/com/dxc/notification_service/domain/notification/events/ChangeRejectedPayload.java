package com.dxc.notification_service.domain.notification.events;

public record ChangeRejectedPayload(
        String changeKey,
        String justification,
        String changeLeadId,
        String changeManagerId
) {}