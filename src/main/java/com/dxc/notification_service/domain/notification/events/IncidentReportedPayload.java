package com.dxc.notification_service.domain.notification.events;

public record IncidentReportedPayload(
        String incidentId,
        String changeKey,
        String incidentManagerId,
        String description
) {}