package com.dxc.notification_service.domain.notification.events;

public record ChangeApprovedPayload(
        String changeKey,
        String changeManagerId,
        String changeLeadId
) {}