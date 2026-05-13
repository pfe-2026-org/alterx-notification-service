package com.dxc.notification_service.domain.notification.events;

public record ChangeReviewCreatedPayload(
        String changeKey,
        String supplierManagerId
) {}