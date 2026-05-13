package com.dxc.notification_service.domain.notification.events;

public record ActionAutoRejectedPayload(
        String changeKey,
        String actionId,
        String expiredTeamLeadId,
        String changeLeadId
) {}