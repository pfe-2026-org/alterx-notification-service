package com.dxc.notification_service.domain.notification.events;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;
public record ChangeExecutionStartedPayload(
        String eventId, String changeId, String changeKey, String title,
        String changeClientCampusId, String changeLeadId,
        List<String> teamLeadIds, String occurredOn
) {}