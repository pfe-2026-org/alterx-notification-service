package com.dxc.notification_service.domain.notification.events;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ChangeScheduledPayload(
        String eventId, String changeId, String changeKey, String changeTitle,
        String changeClientCampusId, String changeLeadId,
        String externalScheduleId, String occurredOn
) {}