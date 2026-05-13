package com.dxc.notification_service.domain.notification.events;



public record ActionReviewCreatedPayload(
        String changeKey,
        String actionId,
        String teamLeadId,
        String deadline
) {}