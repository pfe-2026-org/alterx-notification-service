package com.dxc.notification_service.domain.notification.events;

import com.fasterxml.jackson.annotation.JsonProperty;


public record OutsourcingManagerDecisionPayload(
        String eventId, String changeId, String changeKey, String changeTitle,
        String changeClientCampusId, String changeLeadId,
        String decision, String justification, String occurredOn
) {}