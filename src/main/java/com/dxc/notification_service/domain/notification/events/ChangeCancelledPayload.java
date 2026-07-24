package com.dxc.notification_service.domain.notification.events;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;
public record ChangeCancelledPayload(
        String eventId, String changeId, String changeKey,
        String changeClientCampusId, String changeTitle,
        String cancelledByChangeLeadId, List<String> teamLeadIds,
        String occurredOn
) {}